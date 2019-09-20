package nl.xup.ideaboard.model;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

/**
 * Container object for ideas.
 *
 * @author Minto van der Sluis
 */
@JsonPropertyOrder({"ideas"})
@JsonDeserialize(builder = Ideas.Builder.class)
public class Ideas implements Serializable {

  // -------------------------------------------------------------------------
  // Constants
  // -------------------------------------------------------------------------

  public static final long serialVersionUID = 1L;

  static final String MSG_REQUIRED_ARGUMENT = "Required argument '%s' not set";
  static final String MSG_INVALID_OPTIONAL = "Invalid use of Optional for argument '%s', please use Optional correctly!";
  static final String MSG_INVALID_TYPE = "Invalid type '%s' for argument '%s',";

  // -------------------------------------------------------------------------
  // Class attributes
  // -------------------------------------------------------------------------

  private static ObjectMapper objectMapper;

  // -------------------------------------------------------------------------
  // Object attributes
  // -------------------------------------------------------------------------

  private final Collection<Idea> ideas;

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  protected Ideas( final Builder builder ) {
    // Argument validation
    Objects.requireNonNull( builder, () -> String.format( MSG_REQUIRED_ARGUMENT, "builder" ) );

    Objects.requireNonNull( builder.ideas, () -> String.format( MSG_REQUIRED_ARGUMENT, "ideas" ) );

    // Backward compatible
    this.ideas = Collections.unmodifiableCollection( builder.ideas );
  }

  // -------------------------------------------------------------------------
  // Interface
  // -------------------------------------------------------------------------

  public String toJson() throws IOException {
    return getObjectMapper().writeValueAsString( this );
  }

  public static String toJson( final Ideas ideas ) throws IOException {
    return ideas.toJson();
  }

  public static Ideas fromJson( final String json ) throws IOException {
    return getObjectMapper().readerFor( Ideas.class ).readValue( json );
  }

  // -------------------------------------------------------------------------
  // Getters
  // -------------------------------------------------------------------------

  /**
   * Gets the collection of ideas.
   *
   * @return Collection with Idea object
   */
  @JsonProperty("ideas")
  public Collection<Idea> getIdeas() {
    return ideas;
  }

  // -------------------------------------------------------------------------
  // Inner classes
  // -------------------------------------------------------------------------

  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
  public static class Builder {

    // -------------------------------------------------------------------------
    // Object attributes
    // -------------------------------------------------------------------------

    private Collection<Idea> ideas = new ArrayList<>();

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    protected Builder() {
    }

    protected Builder( final Ideas object ) {
      // Parameter validation
      Objects.requireNonNull( object, () -> String.format( MSG_REQUIRED_ARGUMENT, "object" ) );

      this.ideas.addAll( object.getIdeas() );
    }

    // -------------------------------------------------------------------------
    // Builder methods
    // -------------------------------------------------------------------------

    @JsonProperty(value = "ideas", required = true)
    public Builder withIdeas( final Collection<Idea> ideas ) {
      // Argument validation
      Objects.requireNonNull( ideas, () -> String.format( MSG_REQUIRED_ARGUMENT, "ideas" ) );

      this.ideas.clear();
      this.ideas.addAll( ideas );
      return this;
    }

    public Builder addIdea( final Function<Idea.Builder, Idea> function ) {
      // Argument validation
      Objects.requireNonNull( function, () -> String.format( MSG_REQUIRED_ARGUMENT, "lambda" ) );

      Idea idea = function.apply( new Idea.Builder() );
      return addIdea( idea );
    }

    public Builder addIdea( final Idea idea ) {
      // Argument validation
      Objects.requireNonNull( idea, () -> String.format( MSG_REQUIRED_ARGUMENT, "idea" ) );

      this.ideas.add( idea );
      return this;
    }

    public Ideas build() {
      return new Ideas( this );
    }
  }

  // -------------------------------------------------------------------------
  // Private methods
  // -------------------------------------------------------------------------

  protected static ObjectMapper getObjectMapper() {
    if ( null == objectMapper ) {
      objectMapper = new ObjectMapper()
          .configure( DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false )
          .configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false )
          .configure( SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false )
          .setSerializationInclusion( Include.NON_NULL )
          .setSerializationInclusion( Include.NON_ABSENT )
          .setSerializationInclusion( Include.NON_EMPTY )
          .registerModule( new ParameterNamesModule() )
          .registerModule( new Jdk8Module() )
          .registerModule( new JavaTimeModule() );
    }
    return objectMapper;
  }
}
