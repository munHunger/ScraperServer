var express = require("express");
const resolvers = require("./resolvers");
const { ApolloServer } = require("apollo-server-express");
const { typeDefs } = require("./schema");
const http = require("http");
const logger = require("./logger").logger("server");

startBackend(5001);

/**
 * Start the backend graphql server on the given port
 * @param {Number} port the port to start on
 */
function startBackend(port) {
  const app = express();

  const server = new ApolloServer({
    typeDefs,
    resolvers
  });

  server.applyMiddleware({
    app
  });

  const httpServer = http.createServer(app);
  server.installSubscriptionHandlers(httpServer);

  httpServer.listen(port, () => {
    logger.info(
      `Server ready at http://localhost:${port}${server.graphqlPath}`
    );
    logger.info(
      `Subscriptions ready at ws://localhost:${port}${server.subscriptionsPath}`
    );
  });
}

/**
 * stop the server
 * @returns {void}
 */
function stopServer() {
  server.close();
}
