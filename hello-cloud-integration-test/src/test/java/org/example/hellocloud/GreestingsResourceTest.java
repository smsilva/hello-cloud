package org.example.hellocloud;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GreestingsResourceTest {

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

}
