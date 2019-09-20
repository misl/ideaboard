package nl.xup.ideaboard.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Simple class for defining Idea objects.
 *
 * @author Minto van der Sluis
 */
@JsonPropertyOrder({"title", "description", "createdAt", "updatedAt"})
@JsonDeserialize(builder = Idea.Builder.class)
public class Idea implements Serializable {

  // -------------------------------------------------------------------------
  // Constants
  // -------------------------------------------------------------------------

  public static final long serialVersionUID = 1L;

  // -------------------------------------------------------------------------
  // Object attributes
  // -------------------------------------------------------------------------

  private final String title;
  private final String description;
  private final Instant createdAt;
  private final Instant updatedAt;

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  private Idea( final Builder builder ) {
    Objects.requireNonNull( builder, () -> String.format( Ideas.MSG_REQUIRED_ARGUMENT, "builder" ) );

    Objects.requireNonNull( builder.title, () -> String.format( Ideas.MSG_REQUIRED_ARGUMENT, "title" ) );

    this.title = builder.title;
    this.description = builder.description;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
  }

  // -------------------------------------------------------------------------
  // Getters
  // -------------------------------------------------------------------------

  /**
   * Gets the title of the idea.
   *
   * @return String with the title
   */
  @JsonProperty(value = "title")
  public String getTitle() {
    return title;
  }

  /**
   * Gets the description of the idea.
   *
   * @return String with the description
   */
  @JsonProperty(value = "description")
  public String getDescription() {
    return description;
  }

  /**
   * Gets the creation timestamp of the idea.
   *
   * @return the creation timestamp
   */
  @JsonProperty(value = "createdAt")
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the last update timestamp of the idea.
   *
   * @return the update timestamp
   */
  @JsonProperty(value = "updatedAt")
  public Instant getUpdatedAt() {
    return updatedAt;
  }

  // -------------------------------------------------------------------------
  // Inner classes
  // -------------------------------------------------------------------------

  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
  public static final class Builder {

    // -------------------------------------------------------------------------
    // Object attributes
    // -------------------------------------------------------------------------

    private String title;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public Builder() {
    }

    public Builder( final Idea object ) {
      this.title = object.getTitle();
      this.description = object.getDescription();
      this.createdAt = object.getCreatedAt();
      this.updatedAt = object.getUpdatedAt();
    }

    // -------------------------------------------------------------------------
    // Builder methods
    // -------------------------------------------------------------------------

    @JsonProperty(value = "title")
    public Builder withTitle( final String title ) {
      // Argument validation
      Objects.requireNonNull( title, () -> String.format( Ideas.MSG_REQUIRED_ARGUMENT, "title" ) );

      this.title = title;
      return this;
    }

    @JsonProperty(value = "description")
    public Builder withDescription( final String description ) {
      // Argument validation
      Objects.requireNonNull( description, () -> String.format( Ideas.MSG_REQUIRED_ARGUMENT, "description" ) );

      this.description = description;
      return this;
    }

    @JsonProperty(value = "createdAt")
    public Builder withCreatedAt( final Instant createdAt ) {
      // Argument validation
      Objects.requireNonNull( createdAt, () -> String.format( Ideas.MSG_REQUIRED_ARGUMENT, "createdAt" ) );

      this.createdAt = createdAt;
      return this;
    }

    @JsonProperty(value = "updatedAt")
    public Builder withUpdatedAt( final Instant updatedAt ) {
      // Argument validation
      Objects.requireNonNull( updatedAt, () -> String.format( Ideas.MSG_REQUIRED_ARGUMENT, "updatedAt" ) );

      this.updatedAt = updatedAt;
      return this;
    }

    public Idea build() {
      return new Idea( this );
    }
  }
}
