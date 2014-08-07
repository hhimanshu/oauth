package com.self.oauth.business.oauth;


import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

public class CryptoGeneratorTest {
	@Test
	public void testGetClientIdTwiceReturnsDifferntClientIds() {
		final CryptoGenerator cryptoGenerator = new CryptoGenerator("testClientId");
		final String email = "uniqueEmail@gmail.com";
		final String userExternalId = UUID.randomUUID().toString();

		assertNotEquals(cryptoGenerator.getClientId(email, userExternalId), cryptoGenerator.getClientId(email, userExternalId));
	}
}
