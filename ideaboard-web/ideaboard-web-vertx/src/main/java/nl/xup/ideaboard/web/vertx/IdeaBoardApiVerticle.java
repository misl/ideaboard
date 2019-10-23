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
    // Load API Specification
    OpenAPI3RouterFactory.create( this.vertx, "api/ideaboard-openapi.yaml", openAPI3RouterFactoryResult -> {
      if ( openAPI3RouterFactoryResult.failed() ) {
        // Something when wrong during router factory initialization
        Throwable exception = openAPI3RouterFactoryResult.cause();
        future.fail( exception );
      }

      // Specification loaded with success
      OpenAPI3RouterFactory routerFactory = openAPI3RouterFactoryResult.result();

      // Add an handler with operationId
      routerFactory.addHandlerByOperationId("getIdeas", routingContext -> {
        // Load the parsed parameters
        RequestParameters params = routingContext.get("parsedParameters");
        // Handle listPets operation
        RequestParameter limitParameter = params.queryParameter(/* Parameter name */ "limit");
        if (limitParameter != null) {
          // limit parameter exists, use it!
          Integer limit = limitParameter.getInteger();
        } else {
          // limit parameter doesn't exist (it's not required).
          // If it's required you don't have to check if it's null!
        }
        routingContext.response().setStatusMessage("OK").end();
      });
      // Add a failure handler
      routerFactory.addFailureHandlerByOperationId("getIdeas", routingContext -> {
        // This is the failure handler
        Throwable failure = routingContext.failure();
        if (failure instanceof ValidationException )
          // Handle Validation Exception
          routingContext.response()
              .setStatusCode(400)
              .setStatusMessage("ValidationException thrown! " + ((ValidationException) failure).type().name())
              .end();
      });

      // Add a security handler
//      routerFactory.addSecurityHandler("api_key", routingContext -> {
//        // Handle security here
//        routingContext.next();
//      });

      // Before router creation you can enable/disable various router factory behaviours
      RouterFactoryOptions factoryOptions = new RouterFactoryOptions()
          .setMountResponseContentTypeHandler(true); // Mount ResponseContentTypeHandler automatically

      // Now you have to generate the router
      Router router = routerFactory.setOptions(factoryOptions).getRouter();

      // Now you can use your Router instance
      server = vertx.createHttpServer(new HttpServerOptions().setPort(8085).setHost("localhost"));
      server.requestHandler(router).listen((ar) -> {
        if ( ar.succeeded() ) {
          logger.info( "Server started on port " + ar.result().actualPort() );
          future.complete();
        } else {
          logger.error( "oops, something went wrong during server initialization", ar.cause() );
          future.fail( ar.cause() );
        }
      });
    });
  }

  @Override
  public void stop() {
    this.server.close();
  }
}
