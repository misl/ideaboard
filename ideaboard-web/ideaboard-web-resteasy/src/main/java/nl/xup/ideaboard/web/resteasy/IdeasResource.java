package nl.xup.ideaboard.web.resteasy;

import nl.xup.ideaboard.api.model.Ideas;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

@Path("/ideas")
public class IdeasResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Ideas getIdeas() {
    Ideas ideas = new Ideas.Builder()
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "First idea" )
            .withDescription( "This is an awesome idea." )
            .build() )
        .addIdea( ideaBuilder -> ideaBuilder
            .withTitle( "Second idea" )
            .withDescription( "Another idea." )
            .build() )
        .build();

    return ideas;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createIdea( @Context UriInfo requestUri ) {
    UUID ideaId = UUID.randomUUID();
    URI ideaUri = requestUri.getAbsolutePathBuilder().path( ideaId.toString() ).build();

    return Response
        .status( Response.Status.ACCEPTED )
        .header( "Location", ideaUri.toASCIIString() )
        .build();
  }
}