package nl.xup.ideaboard.web.vertx.resources;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.contract.RouterFactoryOptions;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import nl.xup.ideaboard.api.model.Idea;
import nl.xup.ideaboard.api.model.Ideas;
import nl.xup.ideaboard.web.vertx.services.IdeaService;

import java.io.IOException;

public class IdeaBoardVerticle extends AbstractVerticle {

  Logger logger = LoggerFactory.getLogger( IdeaBoardVerticle.class );

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  HttpServer server;

  IdeaService ideaService;

  // --------------------------------------------------------------------------
  // Contruction
  // --------------------------------------------------------------------------

  public IdeaBoardVerticle( final IdeaService service ) {
    ideaService = service;
  }

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
            Ideas ideas = ideaService.list();
            try {
              // Handle listPets operation
              routingContext.response().setStatusMessage( "getIdeas" ).end( ideas.toJson().toString() );
            } catch ( IOException iox ) {
              routingContext.response().setStatusMessage( iox.getMessage() ).setStatusCode( 500 ).end();
            }
          });
          routerFactory.addHandlerByOperationId("createIdea", routingContext -> {
            try {
              Idea idea = Idea.fromJson( routingContext.getBodyAsString() );
              ideaService.add( idea );
              // Handle listPets operation
              routingContext.response().setStatusMessage("createIdea").end();
            } catch ( IOException iox ) {
              routingContext.response().setStatusMessage( iox.getMessage() ).setStatusCode( 500 ).end();
            }
          });
          routerFactory.addHandlerByOperationId("getIdea", routingContext -> {
            Idea idea = ideaService.get(routingContext.pathParam("ideaId"));
            if ( idea != null ) {
              try {
                // Handle listPets operation
                routingContext.response().setStatusMessage( "getIdea" ).end( idea.toJson().toString() );
              } catch ( IOException iox ) {
                routingContext.response().setStatusMessage( iox.getMessage() ).setStatusCode( 500 ).end();
              }
            } else {
              routingContext.response().setStatusMessage( "getIdea" ).setStatusCode( 404 ).end();
            }
          });
          routerFactory.addHandlerByOperationId("updateIdea", routingContext -> {
            Idea idea = ideaService.get(routingContext.pathParam("ideaId"));
            if ( idea != null ) {
              try {
                Idea newData = new Idea.Builder( Idea.fromJson( routingContext.getBodyAsString() ) )
                    .withId( idea.getId() )
                    .build();
                ideaService.update( newData );
                // Handle listPets operation
                routingContext.response().setStatusMessage("updateIdea").end();
              } catch ( IOException iox ) {
                routingContext.response().setStatusMessage( iox.getMessage() ).setStatusCode( 500 ).end();
              }
            } else {
              routingContext.response().setStatusMessage( "updateIdea" ).setStatusCode( 404 ).end();
            }
          });
          routerFactory.addHandlerByOperationId("removeIdea", routingContext -> {
            Idea idea = ideaService.get(routingContext.pathParam("ideaId"));
            if ( idea != null ) {
              ideaService.remove( idea.getId() );
              // Handle listPets operation
              routingContext.response().setStatusMessage( "removeIdea" ).end();
            } else {
              routingContext.response().setStatusMessage( "removeIdea" ).setStatusCode( 404 ).end();
            }
          });
//          routerFactory.mountServicesFromExtensions();

          // Add a security handler
//          routerFactory.addSecurityHandler("api_key", JWTAuthHandler.create(jwtAuth));

          // Now you have to generate the router
          Router router = routerFactory.getRouter();

          // TODO: Output feedback on which routes became available

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
