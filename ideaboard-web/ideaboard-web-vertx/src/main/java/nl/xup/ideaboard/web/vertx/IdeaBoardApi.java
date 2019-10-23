package nl.xup.ideaboard.web.vertx;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.reactivex.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class IdeaBoardApi {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger( IdeaBoardApi.class );

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  @Inject
  private Vertx vertx;

  private String deploymentId;

  // --------------------------------------------------------------------------
  // Lifecycle operations
  // --------------------------------------------------------------------------

  void onStart( @Observes StartupEvent ev ) {
    startVerticle();
  }

  void onStop( @Observes ShutdownEvent ev ) {
    stopVerticle();
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  private void startVerticle() {
    LOGGER.info( "Starting IdeaBoard API" );
    IdeaBoardApiVerticle verticle = new IdeaBoardApiVerticle();
    vertx.deployVerticle( verticle, handler -> {
      if ( handler.succeeded() ) {
        deploymentId = handler.result();
        LOGGER.info( "IdeaBoard API started" );
      }
    } );
  }

  private void stopVerticle() {
    LOGGER.info( "Stopping IdeaBoard API" );
    vertx.undeploy( deploymentId, handler -> {
      if ( handler.succeeded() ) {
        LOGGER.info( "IdeaBoard API stopped" );
      }
    } );
  }
}
