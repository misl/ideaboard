package nl.xup.ideaboard.messaging.mqtt.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

/**
 * A generic messaging handler to log messages.
 */
@ApplicationScoped
public class MessageLoggerService {

  // --------------------------------------------------------------------------
  // Class attributses
  // --------------------------------------------------------------------------

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger( MessageLoggerService.class );

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  @ConfigProperty(name = "collect.messaging.logger.enable", defaultValue = "false")
  Boolean isLogEnabled;

  // --------------------------------------------------------------------------
  // Interface
  // --------------------------------------------------------------------------

  /**
   * When enabled logs all messages passing through.
   *
   * @param message The message to be logged.
   */
  public void log( final Message<?> message ) {
    Objects.requireNonNull( message );

    if ( isLogEnabled ) {
      LOGGER.info( message.toString() );
    }
  }
}
