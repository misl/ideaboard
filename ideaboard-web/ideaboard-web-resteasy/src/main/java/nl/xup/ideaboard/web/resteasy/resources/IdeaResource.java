package nl.xup.ideaboard.web.resteasy.resources;

import nl.xup.ideaboard.api.model.Idea;
import nl.xup.ideaboard.web.resteasy.services.IdeaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/idea/{ideaId}")
public class IdeaResource {

  @Inject
  IdeaService ideaService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Idea getIdea( @PathParam("ideaId") String ideaId ) {
    return ideaService.get( ideaId );
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateIdea( @PathParam("ideaId") String ideaIdStr, Idea idea ) {
    Response response = null;
    Idea updatedIdea = ideaService.update( idea );
    if ( null == updatedIdea ) {
      response =  Response.status( Response.Status.NOT_FOUND ).build();
    } else {
      response = Response.ok().build();
    }
    return response;
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response removeIdea( @PathParam("ideaId") String ideaIdStr ) {
    Response response = null;
    Idea removedIdea = ideaService.remove( ideaIdStr );
    if ( null == removedIdea ) {
      response =  Response.status( Response.Status.NOT_FOUND ).build();
    } else {
      response = Response.ok( removedIdea ).build();
    }
    return response;
  }
}