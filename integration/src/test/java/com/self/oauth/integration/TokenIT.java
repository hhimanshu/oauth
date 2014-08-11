package com.self.oauth.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Test;

public class TokenIT {
	@Test
	public void testValidToken() throws IOException {
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

	@Nonnull
	private static String getAuthCode(@Nonnull final String email, @Nonnull final String userExternalId,
	                                  @Nonnull final String clientId, @Nonnull final String clientSecret) {
		final String toHash = email + ":" + userExternalId + ":" + clientId + ":" + clientSecret;
		final String hashedAuthCode;
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(toHash.getBytes());
			hashedAuthCode = new BigInteger(1, messageDigest.digest()).toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("MD5 algorithm not found");
		}
		return hashedAuthCode;
	}

	@Nonnull
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper()
				.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false /* force ISO8601 */)
				.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true)
				.configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, true)
				.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
	}
}
