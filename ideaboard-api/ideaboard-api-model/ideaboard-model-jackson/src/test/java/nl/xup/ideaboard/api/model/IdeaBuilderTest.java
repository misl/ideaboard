package nl.xup.ideaboard.api.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for Idea.Builder
 *
 * @author Minto van der Sluis
 */
public class IdeaBuilderTest {

  // -------------------------------------------------------------------------
  // Test cases
  // -------------------------------------------------------------------------

  @Test
  public void testBuilderMinimal() {
    // prepare
    Idea.Builder builder = new Idea.Builder()
        .withTitle( "title" )
        .withCreatedAt( Instant.now() );

    // execute
    Idea data = builder.build();

    // verify
    assertThat( data.getTitle() ).isEqualTo( "title" );
    assertThat( data.getDescription() ).isNull();
    assertThat( data.getCreatedAt() ).isNotNull();
    assertThat( data.getUpdatedAt() ).isNull();
  }

  @Test
  public void testBuilderWithAll() {
    // prepare
    Idea.Builder builder = new Idea.Builder()
        .withTitle( "title" )
        .withDescription( "desc" )
        .withCreatedAt( Instant.now() )
        .withUpdatedAt( Instant.now() );

    // execute
    Idea data = builder.build();

    // verify
    assertThat( data.getTitle() ).isEqualTo( "title" );
    assertThat( data.getDescription() ).isEqualTo( "desc" );
    assertThat( data.getCreatedAt() ).isNotNull();
    assertThat( data.getUpdatedAt() ).isNotNull();
  }

  @Test
  public void testBuilderCloneConstructor() {
    // prepare
    Idea original = new Idea.Builder()
        .withTitle( "title" )
        .withDescription( "desc" )
        .withCreatedAt( Instant.now() )
        .withUpdatedAt( Instant.now() )
        .build();

    // execute
    Idea clone = new Idea.Builder( original ).build();

    // verify
    assertThat( clone.getTitle() ).isEqualTo( "title" );
    assertThat( clone.getDescription() ).isEqualTo( "desc" );
    assertThat( clone.getCreatedAt() ).isNotNull();
    assertThat( clone.getUpdatedAt() ).isNotNull();
  }

  // --------------------------------------------------------------------------
  // Checking error scenarios
  // --------------------------------------------------------------------------

  @Test
  public void testBuilderWithNullTitle() {
    // prepare
    Idea.Builder builder = new Idea.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.withTitle( null ) );
  }

  @Test
  public void testBuilderWithNullDescription() {
    // prepare
    Idea.Builder builder = new Idea.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.withDescription( null ) );
  }

  @Test
  public void testBuilderWithNullCreatedAt() {
    // prepare
    Idea.Builder builder = new Idea.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.withCreatedAt( null ) );
  }

  @Test
  public void testBuilderWithNullUpdatedAt() {
    // prepare
    Idea.Builder builder = new Idea.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.withUpdatedAt( null ) );
  }

  @Test
  public void testBuildUsingNoTitle() {
    // prepare
    Idea.Builder builder = new Idea.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.build() );
  }

  @Test
  public void testBuilderConstructorNullClone() {
    // prepare

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> new Idea.Builder( (Idea) null ) );
  }
}
