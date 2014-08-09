package com.self.oauth.business.oauth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;

public class UniqueIdGenerator {
	private String serverPrivateKey;

	@SuppressWarnings("UnusedDeclaration")
	public UniqueIdGenerator() {
	}

	public UniqueIdGenerator(@Nonnull final String serverPrivateKey) {
		this.serverPrivateKey = serverPrivateKey;
	}

	@Nonnull
	public String getClientId(@Nonnull final String email, @Nonnull final String userExternalId) {
		return getHashedSecret(getStringForClientId(email, userExternalId));
	}

	@Nonnull
	private static String getHashedSecret(@Nonnull final String stringToHash) {
		final MessageDigest messageDigest;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("can not generate client id: " + e.getMessage());
		}

		final String randomString = Integer.toString(new SecureRandom(stringToHash.getBytes()).nextInt());
		messageDigest.update(randomString.getBytes());

		return new BigInteger(1, messageDigest.digest()).toString();
	}

	@Nonnull
	private String getStringForClientId(@Nonnull final String email, @Nonnull final String userExternalId) {
		/**
		 * generating clientId for same values will be different for security reasons.
		 * This is why once clientId created will be stored in database.
		 * The notion of adding LocalDateTime.now().getNano() is to make sure if we ever need to generate/refresh
		 * a new clientId, it is possible with the same data passed in.
		 */
		return ":" + email + ":" + serverPrivateKey + userExternalId + ":" + LocalDateTime.now().getNano() + ":";
	}

	@Nonnull
	public String getClientSecret(@Nonnull final String clientId) {
		return getHashedSecret(getStringForClientSecret(clientId));
	}

	@Nonnull
	private String getStringForClientSecret(@Nonnull final String clientId) {
		return ":" + clientId + ":" + serverPrivateKey + ":" + LocalDateTime.now().getNano() + ":";
	}
}
