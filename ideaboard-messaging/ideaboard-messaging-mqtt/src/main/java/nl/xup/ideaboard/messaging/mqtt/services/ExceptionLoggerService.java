package nl.xup.ideaboard.messaging.mqtt.services;

import nl.xup.ideaboard.messaging.mqtt.exceptions.FailedMessageException;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

/**
 * A generic messaging handler to log failing messages.
 */
@ApplicationScoped
public class ExceptionLoggerService {

  // --------------------------------------------------------------------------
  // Class attributses
  // --------------------------------------------------------------------------

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger( ExceptionLoggerService.class );

  // --------------------------------------------------------------------------
  // Interface
  // --------------------------------------------------------------------------

  /**
   * Logs all failing messages passing through.
   *
   * @param exception The exception containing the failure.
   */
  public void log( final FailedMessageException exception ) {
    Objects.requireNonNull( exception );

    LOGGER.warn( "Failed to process message:\ncause:{}\nmessage='{}'",
        exception.getMessage(), exception.getReceivedMessage().toString() );
  }
}
