package nl.xup.ideaboard.web.vertx;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.contract.RouterFactoryOptions;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.api.RequestParameter;
import io.vertx.reactivex.ext.web.api.RequestParameters;
import io.vertx.reactivex.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;

public class IdeaBoardApiVerticle extends AbstractVerticle {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  HttpServer server;
  Logger logger = LoggerFactory.getLogger( IdeaBoardApiVerticle.class );

  // --------------------------------------------------------------------------
  // AbstractVerticle overrides
  // --------------------------------------------------------------------------

  @Override
  public void start( Future<Void> future ) throws Exception {
    logger.info( "IdeaBoard Rest API is starting" );
    // Load API Specification
    OpenAPI3RouterFactory.rxCreate( this.vertx, "api/ideaboard-openapi.yaml" )
        .flatMap( routerFactory -> {
          // Spec loaded with success. router factory contains OpenAPI3RouterFactory
          // Set router factory options.
          RouterFactoryOptions options = new RouterFactoryOptions().setOperationModelKey("openapi_model");
          // Mount the options
          routerFactory.setOptions(options);
          // Add an handler with operationId
          routerFactory.addHandlerByOperationId("getIdeas", routingContext -> {
            // Handle listPets operation
            routingContext.response().setStatusMessage("Called getIdeas").end();
          });
          routerFactory.addHandlerByOperationId("createIdea", routingContext -> {
            // Handle listPets operation
            routingContext.response().setStatusMessage("Called createIdea").end();
          });
          routerFactory.addHandlerByOperationId("getIdea", routingContext -> {
            // Handle listPets operation
            routingContext.response().setStatusMessage("Called getIdea").end();
          });
          routerFactory.addHandlerByOperationId("updateIdea", routingContext -> {
            // Handle listPets operation
            routingContext.response().setStatusMessage("Called updateIdea").end();
          });
          routerFactory.addHandlerByOperationId("removeIdea", routingContext -> {
            // Handle listPets operation
            routingContext.response().setStatusMessage("Called removeIdea").end();
          });
          routerFactory.mountServiceFromTag(  )

          // Add a security handler
//          routerFactory.addSecurityHandler("api_key", JWTAuthHandler.create(jwtAuth));

          // Now you have to generate the router
          Router router = routerFactory.getRouter();

          // Now you can use your Router instance
          server = vertx.createHttpServer(new HttpServerOptions().setPort(8088).setHost("localhost"));
          return server.requestHandler(router).rxListen();
        })
        .subscribe( httpServer -> {
          // Server up and running
          logger.info( "IdeaBoard Rest API has started" );
        }, throwable -> {
          // Error during router factory instantiation or http server start
        });
  }

  @Override
  public void stop() {
    this.server.close();
    logger.info( "IdeaBoard Rest API has stopped" );
  }
}
