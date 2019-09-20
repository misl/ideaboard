package nl.xup.ideaboard.web.resteasy;

import nl.xup.ideaboard.api.model.Idea;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/ideas/{ideaId}")
public class IdeaResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Idea getIdea( @PathParam("ideaId") String ideaId ) {
    Idea idea = new Idea.Builder()
        .withTitle( "Some idea" )
        .withDescription( "Why is this a great idea ..." )
        .build();

    return idea;
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateIdea( @PathParam("ideaId") String ideaIdStr ) {
    try {
      UUID ideaId = UUID.fromString( ideaIdStr );
      return Response.ok().build();
    } catch ( IllegalArgumentException iae ) {
        return Response.status( Response.Status.NOT_FOUND ).build();
    }
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response removeIdea( @PathParam("ideaId") String ideaIdStr ) {
    try {
      UUID ideaId = UUID.fromString( ideaIdStr );
      Idea idea = new Idea.Builder()
          .withTitle( "Deleted idea" )
          .withDescription( "Not so good idea any longer ..." )
          .build();
      return Response.ok( idea ).build();
    } catch ( IllegalArgumentException iae ) {
      return Response.status( Response.Status.NOT_FOUND ).build();
    }
  }
}