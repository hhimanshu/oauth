package com.self.oauth.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

public class ProtectedIT extends AbstractIntegrationTest {
	@Test
	public void testAccessProtectedResourceValid() throws IOException {
		final String clientId;
		final String clientSecret;
		final String email = "integrationTest@gmail.com";
		final String userExternalId = "userExternalId";
		{
			final Client client = ClientBuilder.newClient();
			final Invocation.Builder request = client.target("http://localhost:9090/application/oauth/register").request(MediaType.APPLICATION_JSON);
			final Map<String, String> parameters = new HashMap<>();
			parameters.put("email", email);
			parameters.put("password", "integrationPassword");
			parameters.put("userExternalId", userExternalId);
			final Response response = request.post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
			assertEquals(HttpStatus.SC_OK, response.getStatus());

			final JsonNode jsonReply = getObjectMapper().readTree(response.readEntity(String.class));
			clientId = jsonReply.get("clientId").asText();
			clientSecret = jsonReply.get("clientSecret").asText();
		}

		final String authToken;
		{
			final Client client = ClientBuilder.newClient();
			final Invocation.Builder request = client.target("http://localhost:9090/application/oauth/token").request(MediaType.APPLICATION_JSON);
			final Map<String, String> parameters = new HashMap<>();
			parameters.put("clientId", clientId);
			parameters.put("clientSecret", clientSecret);
			parameters.put("authCode", getAuthCode(email, userExternalId, clientId, clientSecret));
			final Response response = request.post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
			assertEquals(HttpStatus.SC_OK, response.getStatus());
			final JsonNode jsonReply = getObjectMapper().readTree(response.readEntity(String.class));
			assertTrue(jsonReply.has("authToken"));
			assertFalse(jsonReply.get("authToken").asText().isEmpty());
			authToken = jsonReply.get("authToken").asText();
		}

		{
			final Client client = ClientBuilder.newClient();
			final Invocation.Builder request = client.target("http://localhost:9090/application/rest/protected").request(MediaType.APPLICATION_JSON);
			request.header("BEARER", authToken);
			final Response response = request.get();
			assertEquals(HttpStatus.SC_OK, response.getStatus());
			final JsonNode jsonReply = getObjectMapper().readTree(response.readEntity(String.class));
			assertEquals("Hello Secured Client!", jsonReply.get("data").asText());
		}
	}

	@Test
	public void testAccessProtectedResourceHeaderNotPresent() {
		final Client client = ClientBuilder.newClient();
		final Invocation.Builder request = client.target("http://localhost:9090/application/rest/protected").request(MediaType.APPLICATION_JSON);
		final Response response = request.get();
		assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatus());

	}

	@Test
	public void testAccessProtectedResourceInvalidAuthToken() {
		final Client client = ClientBuilder.newClient();
		final Invocation.Builder request = client.target("http://localhost:9090/application/rest/protected").request(MediaType.APPLICATION_JSON);
		request.header("BEARER", "invalidAuthToken");
		final Response response = request.get();
		assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatus());
	}
}
