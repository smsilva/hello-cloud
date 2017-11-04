package org.example.hellocloud.persons.boundary;

import org.example.hellocloud.infra.Repository;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.example.hellocloud.infra.BaseRepository;
import org.example.hellocloud.persons.entity.Person;

@Stateless
@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonsResource {

    @Context
    UriInfo uriInfo;

    @Inject
    @Repository
    BaseRepository<Person> personRepository;

    @GET
    public Response list() {
	List<Person> list = personRepository.listAll();

	JsonArrayBuilder builder = Json.createArrayBuilder();

	list.forEach(p -> {
	    
	    JsonObject json = Json.createObjectBuilder()
		    .add("id", p.getId())
		    .add("name", p.getName())
		    .build();
	    
	    builder.add(json);
	});
	
	JsonArray array = builder.build();
	
	return Response
		.ok(array)
		.build();
    }

    private static final Logger LOG = Logger.getLogger(PersonsResource.class.getName());

    @POST
    public Response post(@Valid Person person) {
	LOG.log(Level.INFO, "post({0})", person);

	if (person == null) {
	    return Response
		    .status(Response.Status.BAD_REQUEST)
		    .build();
	}

	Person inserted;
	try {
	    inserted = personRepository.insert(person);

	    URI uri = uriInfo.getRequestUriBuilder()
		    .path(inserted.getId().toString())
		    .build();

	    return Response
		    .created(uri)
		    .build();
	} catch (Exception ex) {
	    return Response
		    .serverError()
		    .header("reason", ex.getMessage())
		    .build();
	}
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
	Person person = this.personRepository.findById(id);

	if (person == null) {
	    return Response
		    .status(Response.Status.NOT_FOUND)
		    .build();
	}

	return Response
		.ok(person)
		.build();
    }

}
