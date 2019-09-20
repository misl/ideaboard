package nl.xup.ideaboard.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for Ideas.Builder
 *
 * @author Minto van der Sluis
 */
public class IdeasBuilderTest {

  // -------------------------------------------------------------------------
  // Test cases
  // -------------------------------------------------------------------------

  @Test
  public void testBuilderMinimal() {
    // prepare
    Ideas.Builder builder = new Ideas.Builder();

    // execute
    Ideas data = builder.build();

    // verify
    assertThat( data.getIdeas() ).isNotNull();
    assertThat( data.getIdeas() ).isEmpty();
  }

  @Test
  public void testBuilderWithIdea() {
    // prepare
    List<Idea> ideas = new ArrayList<>();
    ideas.add( new Idea.Builder().withTitle( "title" ).build() );
    Ideas.Builder builder = new Ideas.Builder()
        .withIdeas( ideas );

    // execute
    Ideas data = builder.build();

    // verify
    assertThat( data.getIdeas() ).isNotNull();
    assertThat( data.getIdeas() ).isNotEmpty();
    Idea singleIdea = data.getIdeas().toArray( new Idea[data.getIdeas().size()] )[0];
    assertThat( singleIdea.getTitle() ).isEqualTo( "title" );
  }

  @Test
  public void testBuilderAddIdea() {
    // prepare
    Idea idea = new Idea.Builder().withTitle( "title" ).build();
    Ideas.Builder builder = new Ideas.Builder()
        .addIdea( idea );

    // execute
    Ideas data = builder.build();

    // verify
    assertThat( data.getIdeas() ).isNotNull();
    assertThat( data.getIdeas() ).isNotEmpty();
    Idea singleIdea = data.getIdeas().toArray( new Idea[data.getIdeas().size()] )[0];
    assertThat( singleIdea.getTitle() ).isEqualTo( "title" );
  }

  @Test
  public void testBuilderAddIdeaLambda() {
    // prepare
    Ideas.Builder builder = new Ideas.Builder()
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "title" )
            .build()
        );

    // execute
    Ideas data = builder.build();

    // verify
    assertThat( data.getIdeas() ).isNotNull();
    assertThat( data.getIdeas() ).isNotEmpty();
    Idea singleIdea = data.getIdeas().toArray( new Idea[data.getIdeas().size()] )[0];
    assertThat( singleIdea.getTitle() ).isEqualTo( "title" );
  }

  @Test
  public void testBuilderCloneConstructor() {
    // prepare
    Ideas original = new Ideas.Builder()
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "title" )
            .build()
        )
        .build();

    // execute
    Ideas clone = new Ideas.Builder( original ).build();

    // verify
    assertThat( clone.getIdeas() ).isNotNull();
    assertThat( clone.getIdeas() ).isNotEmpty();
    Idea singleIdea = clone.getIdeas().toArray( new Idea[clone.getIdeas().size()] )[0];
    assertThat( singleIdea.getTitle() ).isEqualTo( "title" );
  }

  // --------------------------------------------------------------------------
  // Checking error scenarios
  // --------------------------------------------------------------------------

  @Test
  public void testBuilderWithNullIdeas() {
    // prepare
    Ideas.Builder builder = new Ideas.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.withIdeas( null ) );
  }

  @Test
  public void testBuilderAddNullIdea() {
    // prepare
    Ideas.Builder builder = new Ideas.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.addIdea( (Idea)null ) );
  }

  @Test
  public void testBuilderAddNullIdeaLambda() {
    // prepare
    Ideas.Builder builder = new Ideas.Builder();

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> builder.addIdea( (Function<Idea.Builder, Idea>) null ) );
  }

  @Test
  public void testBuilderConstructorNullClone() {
    // prepare

    // Execute + Verify
    assertThrows( NullPointerException.class, () -> new Ideas.Builder( (Ideas) null ) );
  }
}
