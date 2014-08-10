package com.self.oauth.business.oauth;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

import com.self.oauth.persistence.entities.User;

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

	@Test
	public void testGetAuthCodeForSameUserTwice() {
		final User user = new User(UUID.randomUUID().toString(), "validateAuthCode@gmail.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
		assertEquals(UniqueIdGenerator.getAuthCode(user), UniqueIdGenerator.getAuthCode(user));
	}

	@Test
	public void testGetAuthCodeForDifferentUsers() {
		final User user1 = new User(UUID.randomUUID().toString(), "user1@gmail.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
		final User user2 = new User(UUID.randomUUID().toString(), "user2@gmail.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
		assertNotEquals(UniqueIdGenerator.getAuthCode(user1), UniqueIdGenerator.getAuthCode(user2));
	}
}
