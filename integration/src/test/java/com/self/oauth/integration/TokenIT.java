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

public class TokenIT extends AbstractIntegrationTest {
	@Test
	public void testGetValidToken() throws IOException {
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
		}
	}

	@Test
	public void testGetTokenWithInvalidRegisterDetails() throws IOException {
		final Client client = ClientBuilder.newClient();
		final Invocation.Builder request = client.target("http://localhost:9090/application/oauth/token").request(MediaType.APPLICATION_JSON);
		final Map<String, String> parameters = new HashMap<>();

		final String clientId = "invalidClientId";
		parameters.put("clientId", clientId);

		final String clientSecret = "invalidClientSecret";
		parameters.put("clientSecret", clientSecret);
		parameters.put("authCode", getAuthCode("invalidEmail", "invalidUserId", clientId, clientSecret));
		final Response response = request.post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
		assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());
		final JsonNode jsonReply = getObjectMapper().readTree(response.readEntity(String.class));
		assertTrue("reply must contain error", jsonReply.has("error"));
	}
}
