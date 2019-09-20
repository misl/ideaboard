package nl.xup.ideaboard.api.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for Ideas
 *
 * @author Minto van der Sluis
 */
public class IdeasTest {

  // -------------------------------------------------------------------------
  // Test cases
  // -------------------------------------------------------------------------

  @Test
  public void testSerialize() throws IOException {
    // prepare
    String strStartDate = "2015-08-04T10:11:30Z";
    Instant createdAt = Instant.parse( strStartDate );
    String strEndData = "2015-08-04T10:11:30Z";
    Instant updatedAt = Instant.parse( strEndData );

    Ideas data = new Ideas.Builder()
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "title" )
            .withDescription( "desc" )
            .withCreatedAt( createdAt )
            .withUpdatedAt( updatedAt )
            .build()
        )
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "title2" )
            .build()
        )
        .build();

    // execute
    String result = data.toJson();

    // verify
    assertThat( result ).isEqualTo(
        "{\"ideas\":[{\"title\":\"title\",\"description\":\"desc\",\"createdAt\":\"2015-08-04T10:11:30Z\",\"updatedAt\":\"2015-08-04T10:11:30Z\"},{\"title\":\"title2\"}]}" );
  }

  @Test
  public void testStaticSerialize() throws IOException {
    // prepare
    String strStartDate = "2015-08-04T10:11:30Z";
    Instant createdAt = Instant.parse( strStartDate );
    String strEndData = "2015-08-04T10:11:30Z";
    Instant updatedAt = Instant.parse( strEndData );

    Ideas data = new Ideas.Builder()
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "title" )
            .withDescription( "desc" )
            .withCreatedAt( createdAt )
            .withUpdatedAt( updatedAt )
            .build()
        )
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "title2" )
            .build()
        )
        .build();

    // execute
    String result = Ideas.toJson( data );

    // verify
    assertThat( result ).isEqualTo(
        "{\"ideas\":[{\"title\":\"title\",\"description\":\"desc\",\"createdAt\":\"2015-08-04T10:11:30Z\",\"updatedAt\":\"2015-08-04T10:11:30Z\"},{\"title\":\"title2\"}]}" );
  }

  @Test
  public void testDeSerialize() throws IOException {
    // prepare
    String strCreatedAt = "2015-08-04T10:11:30Z";
    Instant createdAt = Instant.parse( strCreatedAt );
    String strUpdatedAt = "2015-08-04T10:11:30Z";
    Instant updatedAt = Instant.parse( strUpdatedAt );
    String json =
        "{\"ideas\":[{\"title\":\"title\",\"description\":\"desc\",\"createdAt\":\"2015-08-04T10:11:30Z\",\"updatedAt\":\"2015-08-04T10:11:30Z\"},{\"title\":\"title2\"}]}";

    // execute
    Ideas data = Ideas.fromJson( json );

    // verify
    assertThat( data.getIdeas() ).isNotNull();
    assertThat( data.getIdeas().size() ).isEqualTo( 2 );
    Idea[] ideas = data.getIdeas().toArray( new Idea[data.getIdeas().size()] );
    assertThat( ideas[0].getTitle() ).isEqualTo( "title" );
    assertThat( ideas[0].getDescription() ).isEqualTo( "desc" );
    assertThat( ideas[0].getCreatedAt() ).isEqualTo( createdAt );
    assertThat( ideas[0].getUpdatedAt() ).isEqualTo( updatedAt );
    assertThat( ideas[1].getTitle() ).isEqualTo( "title2" );
  }
}
