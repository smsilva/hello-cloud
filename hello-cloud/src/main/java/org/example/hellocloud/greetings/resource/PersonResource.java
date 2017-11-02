package org.example.hellocloud.greetings.resource;

import java.net.URI;
import javax.inject.Inject;
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
import org.example.hellocloud.greetings.control.PersonRepository;
import org.example.hellocloud.greetings.entity.Person;

@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Context
    UriInfo uriInfo;

    @Inject
    PersonRepository personRepository;

    @POST
    public Response post(Person person) {
	if (person == null) {
	    return Response
		    .status(Response.Status.BAD_REQUEST)
		    .build();
	}

	Person inserted = personRepository.insert(person);

	URI uri = uriInfo.getRequestUriBuilder()
		.path(inserted.getId().toString())
		.build();

	return Response
		.created(uri)
		.build();

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
