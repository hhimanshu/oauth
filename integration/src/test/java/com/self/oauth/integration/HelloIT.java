package com.self.oauth.integration;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Test;

public class HelloIT {
    @Test
    public void testHello() {
        final Client client = ClientBuilder.newClient();
        final Response response = client.target("http://localhost:9090/oauth/rest/hello").request().get();
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("Hello World!", response.readEntity(String.class));
    }
}
