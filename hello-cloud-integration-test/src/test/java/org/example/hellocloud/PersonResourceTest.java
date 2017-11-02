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
    public void insertFailure() {
	JsonObject person = Json.createObjectBuilder()
		.add("name", "Silvio Silva #Failure")
		.build();

	Response response = insert(person);

	assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

	assertEquals("Person gender can't be null", response.getHeaderString("reason"));
    }
    
    @Test
    public void insertFailureMessageWithDifferenteLanguages() {
	JsonObject person = Json.createObjectBuilder()
		.add("name", "Name #Failure Dif Lang")
		.build();

	Response response = getTarget()
		.request()
		.header("Accept-Language", "en")
		.post(Entity.json(person.toString()));

	assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	assertEquals("Person gender can't be null", response.getHeaderString("reason"));
	
	response = getTarget()
		.request()
		.header("Accept-Language", "pt")
		.post(Entity.json(person.toString()));

	assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	assertEquals("Sexo da pessoa nao pode ser nulo", response.getHeaderString("reason"));
    }
    
    private String generateName() {
	return String.format("Name #[%s]Ok", this.getList().size());
    }
    
    @Test
    public void insertWithSuccessful() {
	JsonObject person = createValidPerson(generateName());

	Response response = insert(person);

	assertEquals(response.getHeaderString("reason"), Response.Status.CREATED.getStatusCode(), response.getStatus());

	String location = response.getHeaderString("Location");

	assertNotNull(location);
	assertTrue(location.contains(url));
    }

    private JsonObject createValidPerson(String name) {
	JsonObject person = Json.createObjectBuilder()
		.add("name", name)
		.add("gender", "M")
		.build();
	return person;
    }

    private Response insert(JsonObject person) {
	return getTarget()
		.request()
		.header("Accept-Language", "en")
		.post(Entity.json(person.toString()));
		
    }

    @Test
    public void getById() {
	insertWithSuccessful();

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
	insertWithSuccessful();

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
    
    private JsonArray getList() {
	Response response = getTarget()
		.request()
		.get();

	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

	String stringReturned = response.readEntity(String.class);

	return Json.createReader(new ByteArrayInputStream(stringReturned.getBytes()))
		.readArray();
    }

}
