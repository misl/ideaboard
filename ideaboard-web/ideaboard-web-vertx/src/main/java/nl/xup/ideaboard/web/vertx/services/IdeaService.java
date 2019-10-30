package nl.xup.ideaboard.web.vertx.services;

import nl.xup.ideaboard.api.model.Idea;
import nl.xup.ideaboard.api.model.Ideas;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class IdeaService {

  private Map<String, Idea> ideas = new LinkedHashMap();

  public IdeaService() {
    Idea idea = new Idea.Builder()
        .withId( UUID.randomUUID().toString() )
        .withTitle( "First idea" )
        .withDescription( "This is an awesome idea." )
        .withCreatedAt( Instant.now() )
        .build();
    ideas.put( idea.getId(), idea );
    idea = new Idea.Builder()
        .withId( UUID.randomUUID().toString() )
        .withTitle( "Second idea" )
        .withDescription( "Another idea." )
        .withCreatedAt( Instant.now() )
        .build();
    ideas.put( idea.getId(), idea );
  }

  public Ideas list() {
    Ideas.Builder builder = new Ideas.Builder();
    ideas.values().forEach( idea -> builder.addIdea( idea ) );
    return builder.build();
  }

  public Idea get( String id ) {
    Idea idea = null;
    if ( ideas.containsKey( id ) ) {
      idea = ideas.get( id );
    }

    return idea;
  }

  public Idea add( Idea newIdea ) {
    Idea idea = new Idea.Builder( newIdea )
        .withId( UUID.randomUUID().toString() )
        .withCreatedAt( Instant.now() )
        .build();
    ideas.put( idea.getId(), idea );
    return idea;
  }

  public Idea update( Idea idea ) {
    Objects.requireNonNull( idea.getId() );

    Idea oldIdea = get( idea.getId() );
    Idea newIdea = null;
    if ( oldIdea != null ) {
      newIdea = new Idea.Builder( oldIdea )
          .withTitle( idea.getTitle() )
          .withDescription( idea.getDescription() )
          .withUpdatedAt( Instant.now() )
          .build();

      ideas.remove( oldIdea.getId() );
      ideas.put( newIdea.getId(), newIdea );
    }

    return newIdea;
  }

  public Idea remove( String id ) {
    Idea idea = null;
    if ( ideas.containsKey( id ) ) {
      idea = ideas.remove( id );
    }
    return idea;
  }
}
