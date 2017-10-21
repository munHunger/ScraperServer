package se.mulander.cosmos.common.discovery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.glassfish.jersey.client.ClientProperties;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Marcus Münger on 2017-07-20.
 */
@Path("/discover")
@Api(value = "Discovery", description = "Endpoints for finding other services")
public class Scanner
{
    public static String getLocalAddress() throws SocketException
    {
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements(); )
        {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements(); )
            {
                InetAddress addr = a.nextElement();
                String address = addr.getHostAddress();
                if (!address.contains(":") && !address.startsWith("127")) return address;
            }
        }
        return null;
    }

    /**
     * Scans the network for a specific discovery service.
     * It does this by using it's current IP, removing the last byte and iterating over networks.
     * If anyone responds on the address with an OK and the entity "cosmos" then a service has been found.
     * <p>
     * If the computer is offline and thus doesn't have an IP, then 127.0.0.1 will be used
     *
     * @param port The port to scan on(this is ignored for local connections
     * @param path The path to the discovery of the sought service
     * @return An address to the service
     */
    public static String find(int port, String path)
    {
        Client client = null;
        try
        {
            String localIP = getLocalAddress();
            if (localIP == null) return "127.0.0.1";
            String gate = "http://" + localIP.substring(localIP.indexOf("/") + 1, localIP.lastIndexOf(".") + 1);
            String sslGate = "https://" + localIP.substring(localIP.indexOf("/") + 1, localIP.lastIndexOf(".") + 1);

            client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 3);
            client.property(ClientProperties.READ_TIMEOUT, 3);

            for (int i = 1; i < 255; i++)
            {
                Response response = null;
                try
                {
                    response = client.target(String.format("%s%d:%d", gate, i, port)).path(path).request().get();
                    int responseStatus = response.getStatus();
                    String data = response.readEntity(String.class);

                    if (responseStatus == 200 && data.equals("cosmos")) return String.format("%s%d:%d", gate, i, port);
                } catch (ProcessingException e)
                {
                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally
                {
                    if (response != null) response.close();
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            client.close();
        }
        return null;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Discover", notes = "Static point that returns 200 OK, to note that this endpoint is alive. As this is common " + "for all cosmos microservices")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "static OK to note that it is alive")})
    public Response getRecomendations()
    {
        return Response.ok("cosmos").build();
    }
}