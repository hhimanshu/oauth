package com.self.oauth.business.oauth;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

public class CryptoGeneratorTest {
    @Test
    public void testGetClientId() {
        final CryptoGenerator cryptoGenerator = new CryptoGenerator("testClientId");
        final String email = "uniqueEmail@gmail.com";
        final String userExternalId = UUID.randomUUID().toString();

        assertEquals(cryptoGenerator.getClientId(email, userExternalId), cryptoGenerator.getClientId(email, userExternalId));
        assertNotEquals(cryptoGenerator.getClientId(email, userExternalId), cryptoGenerator.getClientId(email + "1", userExternalId));
    }
}