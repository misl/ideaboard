package nl.xup.ideaboard.web.resteasy;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import javax.activation.MimeType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class IdeasResourceTest {

  @Test
  public void testGetIdeas() {
    given()
        .when().get( "/ideas" )
        .then()
        .contentType( ContentType.JSON )
        .statusCode( Response.Status.OK.getStatusCode() );
  }

  @Test
  public void testPostIdea() {
    given()
        .when()
        .contentType( ContentType.JSON )
        .body( "{\"title\":\"First idea\",\"description\":\"This is an awesome idea.\"}" )
        .post( "/ideas" )
        .then()
        .statusCode( Response.Status.ACCEPTED.getStatusCode() );
  }

}