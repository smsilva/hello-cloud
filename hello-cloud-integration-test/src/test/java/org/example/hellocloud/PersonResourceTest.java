package org.example.hellocloud;

import java.io.ByteArrayInputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PersonResourceTest {

    private TestProperties properties;
    private String url;

    @Before
    public void before() throws Exception {
	this.properties = new TestProperties();
	this.url = properties.getProperty("hello.url");
    }
    
    private WebTarget getTarget() {
	return ClientBuilder.newClient()
		.target(url)
		.path("persons");
    }

    @Test
    public void insert() {
	JsonObject person = Json.createObjectBuilder()
		.add("name", "Silvio Silva")
		.build();

	Response response = getTarget()
		.request()
		.post(Entity.json(person.toString()));

	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

	String location = response.getHeaderString("Location");

	assertNotNull(location);
	assertTrue(location.contains(url));
    }

    @Test
    public void getById() {
	insert();

	Response response = getTarget()
		.path("{id}")
		.resolveTemplate("id", 1)
		.request()
		.get();

	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

	String stringReturned = response.readEntity(String.class);

	JsonObject person = Json.createReader(new ByteArrayInputStream(stringReturned.getBytes()))
		.readObject();

	assertNotNull(person);

	String name = person.getString("name");
	assertTrue("Name returned: " + name, name.startsWith("Silvio"));
    }

    @Test
    public void list() {
	insert();

	Response response = getTarget()
		.request()
		.get();

	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

	String stringReturned = response.readEntity(String.class);

	JsonArray array = Json.createReader(new ByteArrayInputStream(stringReturned.getBytes()))
		.readArray();

	assertNotNull(array);
	assertTrue(array.size() > 0);
    }

}
