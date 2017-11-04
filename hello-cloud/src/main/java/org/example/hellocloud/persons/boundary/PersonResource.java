package org.example.hellocloud.persons.boundary;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
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
import org.example.hellocloud.persons.control.Repository;
import org.example.hellocloud.persons.entity.Person;

@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Context
    UriInfo uriInfo;

    @Inject
    Repository<Person> personRepository;

    @GET
    public Response list() {
	List<Person> list = personRepository.listAll();

	return Response
		.ok(list)
		.build();
    }

    @POST
    public Response post(@Valid Person person) {
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
