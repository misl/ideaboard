package nl.xup.ideaboard.messaging.mqtt.exceptions;

import org.eclipse.microprofile.reactive.messaging.Message;

import java.text.MessageFormat;

/**
 * Exception thrown when incoming messages do not comply with expected format.
 */
public class MessageFormatException extends FailedMessageException {

  // --------------------------------------------------------------------------
  // Constants
  // --------------------------------------------------------------------------

  public final static String CONTENT_TYPE_MISMATCH = "Content type mismatch: expected \"{0}\" received \"{1}\"!";

  // --------------------------------------------------------------------------
  // Constructors
  // --------------------------------------------------------------------------

  public MessageFormatException( final String expectedContentType, final String receivedContentType, final Message message ) {
    this( expectedContentType, receivedContentType, message, null );
  }

  public MessageFormatException( final String expectedContentType, final String receivedContentType, final Message message, final Throwable chainedException ) {
    super( MessageFormat.format(CONTENT_TYPE_MISMATCH, expectedContentType, receivedContentType), message, chainedException );
  }
}
