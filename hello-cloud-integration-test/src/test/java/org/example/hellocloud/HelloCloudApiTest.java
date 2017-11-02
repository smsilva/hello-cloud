package org.example.hellocloud;

import java.io.ByteArrayInputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class HelloCloudApiTest {

    private TestProperties properties;
    private String url;

    @Before
    public void before() throws Exception {
	this.properties = new TestProperties();
	this.url = properties.getProperty("hello.url");	
    }

    @Test
    public void greetingsHello() {
	Client client = ClientBuilder.newClient();
	Response response = client
		.target(url)
		.path("greetings")
		.request()
		.get(Response.class);

	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

	JsonObject json = response.readEntity(JsonObject.class);

	assertEquals("Hello!", json.getString("message"));
    }

    @Test
    public void validation() {
	JsonObject person = Json.createObjectBuilder()
		.add("name", "Silvio Silva")
		.build();

	Client client = ClientBuilder.newClient();
	Response response = client
		.target(url)
		.path("greetings")
		.request()
		.post(Entity.json(person.toString()));

	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

	String location = response.getHeaderString("Location");

	assertNotNull(location);
	assertTrue(location.contains(url));
    }

    @Test
    public void get() {
	validation();
		
	Client client = ClientBuilder.newClient();
	Response response = client
		.target(url)
		.path("greetings/{id}")
		.resolveTemplate("id", 1)
		.request()
		.get();

	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

	String stringReturned = response.readEntity(String.class);

	JsonObject person = Json.createReader(new ByteArrayInputStream(stringReturned.getBytes()))
		.readObject();

	assertNotNull(person);
	
	String name = person.getString("name");
	assertTrue("Name returned: " + name, name.contains("Silvio Silva"));
    }

}
