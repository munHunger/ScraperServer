package se.mulander.cosmos.settings.util.swagger;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Marcus Münger on 2017-05-19.
 */
public class Bootstrap extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setHost("");
        beanConfig.setBasePath("/settings/api");
        beanConfig.setResourcePackage("se.mulander.cosmos");
        beanConfig.setScan(true);
    }
}
