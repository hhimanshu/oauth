package com.self.oauth.business.oauth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

public class CryptoGenerator {
    private String serverPrivateKey;

    @SuppressWarnings("UnusedDeclaration")
    public CryptoGenerator() {
    }

    public CryptoGenerator(@Nonnull final String serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }

    @Nonnull
    public String getClientId(@Nonnull final String email, @Nonnull final String userExternalId) {
        final String stringToHash = getStringToHash(email, userExternalId);
        return getHashedClientId(stringToHash);
    }

    @Nonnull
    private static String getHashedClientId(@Nonnull final String stringToHash) {
        final MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("can not generate client id: " + e.getMessage());
        }

        messageDigest.update(stringToHash.getBytes(), 0, stringToHash.length());
        return new BigInteger(1, messageDigest.digest()).toString();
    }

    @Nonnull
    private String getStringToHash(@Nonnull final String email, @Nonnull final String userExternalId) {
        return ":" + email + ":" + serverPrivateKey + userExternalId + ":";
    }
}
