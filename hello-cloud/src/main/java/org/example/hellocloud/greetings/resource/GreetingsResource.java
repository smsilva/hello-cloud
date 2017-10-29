package org.example.hellocloud.greetings.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("greetings")
public class GreetingsResource {
    
    @GET
    public Response get() {
	return Response
		.ok()
		.build();
    }

}
