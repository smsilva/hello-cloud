package org.example.hellocloud;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class HelloCloudTest {
    
    private TestProperties properties;
    
    @Before
    public void before() throws Exception {
	this.properties = new TestProperties();
    }
    
    @Test
    public void properties() throws Exception {
	String greeting = properties.getProperty("hello.greeting");
	assertEquals("Hello!", greeting);
    }
    
    @Test
    public void oracle() {
	String url = properties.getProperty("hello.url");
	
	Client client = ClientBuilder.newClient();
	
	Response response = client
		.target(url)
		.path("greetings")
		.request()
		.get(Response.class);

	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void json() {
	JsonObject json = Json.createObjectBuilder()
		.add("hello.greeting", "Good morning!")
		.build();

	assertTrue(json.containsKey("hello.greeting"));
    }
    
}
