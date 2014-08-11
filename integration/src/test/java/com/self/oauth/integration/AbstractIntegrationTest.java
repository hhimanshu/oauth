package com.self.oauth.integration;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

	@Nonnull
	protected static String getAuthCode(@Nonnull final String email, @Nonnull final String userExternalId,
	                                    @Nonnull final String clientId, @Nonnull final String clientSecret) {
		final String toHash = email + ":" + userExternalId + ":" + clientId + ":" + clientSecret;
		final String hashedAuthCode;
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(toHash.getBytes());
			hashedAuthCode = new BigInteger(1, messageDigest.digest()).toString();
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("MD5 algorithm not found");
		}
		return hashedAuthCode;
	}
}
