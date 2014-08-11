package com.self.oauth.integration;

import javax.annotation.Nonnull;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class AbstractIntegrationTest {
	@Nonnull
	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper()
				.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false /* force ISO8601 */)
				.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true)
				.configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, true)
				.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
	}
}
