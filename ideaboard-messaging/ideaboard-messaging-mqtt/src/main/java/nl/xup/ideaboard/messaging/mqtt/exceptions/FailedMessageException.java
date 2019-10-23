package nl.xup.ideaboard.messaging.mqtt.exceptions;

import org.eclipse.microprofile.reactive.messaging.Message;

/**
 * Exception thrown when incoming messages can not be processed.
 */
public class FailedMessageException extends RuntimeException {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  final Message receivedMessage;

  // --------------------------------------------------------------------------
  // Constructors
  // --------------------------------------------------------------------------

  public FailedMessageException( final String errorMessage, final Message message  ) {
    this( errorMessage, message, null );
  }

  public FailedMessageException( final String errorMessage, final Message message, final Throwable chainedException ) {
    super( errorMessage, chainedException );
    this.receivedMessage = message;
  }

  // --------------------------------------------------------------------------
  // Getters
  // --------------------------------------------------------------------------

  public Message getReceivedMessage() {
    return receivedMessage;
  }
}
