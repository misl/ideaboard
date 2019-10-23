package nl.xup.ideaboard.web.resteasy.resources;

import nl.xup.ideaboard.api.model.Idea;
import nl.xup.ideaboard.api.model.Ideas;
import nl.xup.ideaboard.web.resteasy.services.IdeaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@Path("/ideas")
public class IdeasResource {

  @Inject
  IdeaService ideaService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Ideas getIdeas() {
    return ideaService.list();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createIdea( @Context UriInfo requestUri, Idea idea ) {
    Idea storedIdea = ideaService.add( idea );

    URI ideaUri = requestUri.getAbsolutePathBuilder().path( storedIdea.getId() ).build();
   return Response
        .status( Response.Status.ACCEPTED )
        .header( "Location", ideaUri.toASCIIString() )
        .build();
  }
}