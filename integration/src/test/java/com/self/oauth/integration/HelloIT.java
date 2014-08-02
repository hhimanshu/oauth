package com.self.oauth.integration;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Test;

public class HelloIT {
    @Test
    public void testHelloOAuth() {
        final Client client = ClientBuilder.newClient();
        final Response response = client.target("http://localhost:9090/application/oauth/register").request().get();
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("Hello Oauth", response.readEntity(String.class));
    }

    @Test
    public void testHelloRest() {
        final Client client = ClientBuilder.newClient();
        final Response response = client.target("http://localhost:9090/application/rest/protected").request().get();
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("Hello REST", response.readEntity(String.class));
    }
}
