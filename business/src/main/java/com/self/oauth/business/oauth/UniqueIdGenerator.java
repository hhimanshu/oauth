package com.self.oauth.business.oauth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import com.self.oauth.persistence.entities.User;

public class UniqueIdGenerator {
	private static final String COLON = ":";
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
		final MessageDigest messageDigest = getMessageDigest("MD5");
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
		return COLON + email + COLON + serverPrivateKey + userExternalId + COLON + LocalDateTime.now().getNano() + COLON;
	}

	@Nonnull
	public String getClientSecret(@Nonnull final String clientId) {
		return getHashedSecret(getStringForClientSecret(clientId));
	}

	@Nonnull
	private String getStringForClientSecret(@Nonnull final String clientId) {
		return COLON + clientId + COLON + serverPrivateKey + COLON + LocalDateTime.now().getNano() + COLON;
	}

	@Nonnull
	public static String getAuthCode(@Nonnull final User user) {
		final MessageDigest messageDigest = getMessageDigest("MD5");
		messageDigest.update(getStringForAuthCode(user).getBytes());
		return new BigInteger(1, messageDigest.digest()).toString();
	}

	@Nonnull
	private static String getStringForAuthCode(@Nonnull final User user) {
		return user.getEmail() + COLON + user.getUserExternalId() + COLON + user.getClientId() + COLON + user.getClientSecret();
	}

	@Nonnull
	private static MessageDigest getMessageDigest(@Nonnull final String algorithm) {
		final MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("can not generate client id: " + e.getMessage());
		}
		return messageDigest;
	}

	@Nonnull
	public String getAuthToken(@Nonnull final User user) {
		final int nanoTimeStamp = LocalDateTime.now().getNano();
		final MessageDigest messageDigest = getMessageDigest("MD5");
		messageDigest.update((serverPrivateKey + user.getId() + nanoTimeStamp).getBytes());
		return new BigInteger(1, messageDigest.digest()).toString() + COLON + user.getId() + COLON + nanoTimeStamp;
	}
}
