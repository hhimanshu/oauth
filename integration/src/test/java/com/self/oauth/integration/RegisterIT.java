package com.self.oauth.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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

public class RegisterIT {
    @Test
    public void testRegisterClientValid() throws IOException {
        final Client client = ClientBuilder.newClient();
        final Invocation.Builder request = client.target("http://localhost:9090/application/oauth/register").request(MediaType.APPLICATION_JSON);
	    final Map<String, String> parameters = new HashMap<>();
	    parameters.put("email", "integrationTest@gmail.com");
	    parameters.put("password", "integrationPassword");
	    parameters.put("userExternalId", "userExternalId");
	    final Response response = request.post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
	    assertEquals(HttpStatus.SC_OK, response.getStatus());

	    final JsonNode jsonReply = getObjectMapper().readTree(response.readEntity(String.class));
	    assertTrue("clientId exists in response", jsonReply.has("clientId"));
	    assertFalse("clientId value is not empty", jsonReply.get("clientId").asText().isEmpty());

	    assertTrue("clientSecret exists in response", jsonReply.has("clientSecret"));
	    assertFalse("clientSecret value is not empty", jsonReply.get("clientSecret").asText().isEmpty());
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
