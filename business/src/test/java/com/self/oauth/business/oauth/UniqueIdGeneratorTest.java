package com.self.oauth.business.oauth;


import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

public class UniqueIdGeneratorTest {
	@Test
	public void testGetClientIdTwiceReturnsDifferentClientIdsForSameUser() {
		final UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator("testClientId");
		final String email = "uniqueEmail@gmail.com";
		final String userExternalId = UUID.randomUUID().toString();

		assertNotEquals(uniqueIdGenerator.getClientId(email, userExternalId), uniqueIdGenerator.getClientId(email, userExternalId));
	}

	@Test
	public void testGetClientSecretTwiceReturnsDifferentClientSecretsForSameUser() throws InterruptedException {
		final UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator("testClientId");
		final String email = "uniqueEmail@gmail.com";
		final String userExternalId = UUID.randomUUID().toString();

		final String clientId = uniqueIdGenerator.getClientId(email, userExternalId);
		assertNotEquals(uniqueIdGenerator.getClientSecret(clientId), uniqueIdGenerator.getClientSecret(clientId));
	}
}
