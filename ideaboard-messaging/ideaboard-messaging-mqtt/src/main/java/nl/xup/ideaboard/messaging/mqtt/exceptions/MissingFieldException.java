package nl.xup.ideaboard.messaging.mqtt.exceptions;

import org.eclipse.microprofile.reactive.messaging.Message;

import java.text.MessageFormat;

/**
 * Exception thrown when there is something wrong with the data. This could
 * be either missing fields, fields in unsupported formats, ...
 */
public class MissingFieldException extends FailedMessageException {

  // --------------------------------------------------------------------------
  // Constants
  // --------------------------------------------------------------------------

  public final static String MISSING_FIELD = "Expected field \"{1}\" is empty or missing!";

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  final String objectName;
  final String field;

  // --------------------------------------------------------------------------
  // Constructors
  // --------------------------------------------------------------------------

  public MissingFieldException( final String objectName, final String field, final Message<?> message ) {
    this( objectName, field, message, null );
  }

  public MissingFieldException( final String objectName, final String field, final Message<?> message, final Throwable chainedException ) {
    super( MessageFormat.format( MISSING_FIELD, objectName, field ), message, chainedException );
    this.objectName = objectName;
    this.field = field;
  }

  // --------------------------------------------------------------------------
  // Getters
  // --------------------------------------------------------------------------


  public String getObjectName() {
    return objectName;
  }

  public String getField() {
    return field;
  }
}
