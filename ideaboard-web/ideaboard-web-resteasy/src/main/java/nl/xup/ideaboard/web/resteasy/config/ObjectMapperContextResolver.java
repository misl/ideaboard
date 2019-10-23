package nl.xup.ideaboard.web.resteasy.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

  private final ObjectMapper mapper;

  public ObjectMapperContextResolver() {
    this.mapper = createObjectMapper();
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return mapper;
  }

  private ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper()
        .configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false )
        .configure( SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false )
        .setSerializationInclusion( JsonInclude.Include.NON_NULL )
        .setSerializationInclusion( JsonInclude.Include.NON_ABSENT )
        .setSerializationInclusion( JsonInclude.Include.NON_EMPTY )
        .registerModule( new Jdk8Module() )
        .registerModule( new JavaTimeModule() );
    return mapper;
  }
}