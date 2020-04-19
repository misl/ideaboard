package nl.xup.ideaboard.web.vertx.resources;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import io.vertx.reactivex.core.Vertx;
import nl.xup.ideaboard.web.vertx.services.IdeaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class IdeaBoardVerticleActivator {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger( IdeaBoardVerticleActivator.class );

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  @Inject
  IdeaService ideaService;

  @Inject
  private Vertx vertx;

  @Inject
  private Router router;

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
    router.get( "/test" ).handler( rc -> rc.response().end( "test" ) );

    LOGGER.info( "Starting IdeaBoard API" );
    IdeaBoardVerticle verticle = new IdeaBoardVerticle( ideaService, router );
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
