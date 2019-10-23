package nl.xup.ideaboard.messaging.mqtt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import nl.xup.ideaboard.api.model.Idea;
import nl.xup.ideaboard.messaging.mqtt.exceptions.FailedMessageException;
import nl.xup.ideaboard.messaging.mqtt.exceptions.MissingFieldException;
import nl.xup.ideaboard.messaging.mqtt.services.ExceptionLoggerService;
import nl.xup.ideaboard.messaging.mqtt.services.MessageLoggerService;
import nl.xup.ideaboard.messaging.mqtt.util.FunctionWithException;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

@ApplicationScoped
public class IdeaProcessor {

  // --------------------------------------------------------------------------
  // Class attributses
  // --------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger( IdeaProcessor.class );

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

//  @Inject
  ObjectMapper mapper;

  @Inject
  MessageLoggerService messageLogger;

  @Inject
  ExceptionLoggerService exceptionLogger;

  // --------------------------------------------------------------------------
  // Object construction
  // --------------------------------------------------------------------------

  @PostConstruct
  private void init() {
    mapper = new ObjectMapper()
        .configure( DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false )
        .configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false )
        .configure( SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false )
        .setSerializationInclusion( JsonInclude.Include.NON_NULL )
        .setSerializationInclusion( JsonInclude.Include.NON_ABSENT )
        .setSerializationInclusion( JsonInclude.Include.NON_EMPTY )
        .registerModule( new ParameterNamesModule() )
        .registerModule( new Jdk8Module() )
        .registerModule( new JavaTimeModule() );
  }

  // --------------------------------------------------------------------------
  // Messaging handlers
  // --------------------------------------------------------------------------

  @Incoming("idea-data")
  public Subscriber<MqttMessage<byte[]>> load() {
    return ReactiveStreams.<MqttMessage<byte[]>>builder()
        .filter( Objects::nonNull )
        .onErrorResumeWith( e -> {
          if ( e instanceof FailedMessageException ) {
            exceptionLogger.log( (FailedMessageException) e );
          } else {
            LOGGER.error( "Message failed:", e );
          }
          return ReactiveStreams.empty();
        } )
        .peek( messageLogger::log )
        .map( wrapper( this::loadData ) )
        .forEach( this::process )
        .build();
  }

  public void process( final Idea idea ) {
    Objects.requireNonNull( idea );

    try {
      LOGGER.info( idea.toString() );
    } catch ( final Exception e ) {
      LOGGER.error( "Message failed:", e );
    }
  }

  // --------------------------------------------------------------------------
  // Lambda's
  // --------------------------------------------------------------------------

  Idea loadData( final MqttMessage<byte[]> rawMessage ) throws IOException {
    String message = new String( rawMessage.getPayload() );
    Idea data = this.mapper.readerFor( Idea.class ).readValue( message );

//    getExpectedValue( rawMessage, "data", KEY_DEVICE_ID, data.getDeviceId() );
//    getExpectedValue( rawMessage, "data", KEY_ENTITY_ID, data.getEntityId().orElse( null ) );
//    getExpectedValue( rawMessage, "data", KEY_VALUES, !data.getValues().isEmpty() );

    return data;
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  private String getExpectedValue( final Message<?> message, final String objectName, final String field, final String value ) throws MissingFieldException {
    if ( value == null || value.trim().isEmpty() ) {
      throw new MissingFieldException( objectName, field, message );
    }
    return value;
  }

  private <T, R, E extends Exception> Function<T, R> wrapper( FunctionWithException<T, R, E> fe ) {
    return arg -> {
      try {
        return fe.apply( arg );
      } catch ( RuntimeException e ) {
        throw e;
      } catch ( Exception e ) {
        throw new RuntimeException( e );
      }
    };
  }

}
