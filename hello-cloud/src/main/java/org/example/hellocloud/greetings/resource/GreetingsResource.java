package org.example.hellocloud.greetings.resource;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.example.hellocloud.Config;

@Path("greetings")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingsResource {
    
    @Inject
    @Config("hello.greeting")
    String greeting;
    
    @GET
    public Response get() {
	JsonObject json = Json.createObjectBuilder()
		.add("message", greeting)
		.build();
	
	return Response
		.ok(json)
		.build();
    }

}
