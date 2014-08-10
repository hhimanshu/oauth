package com.self.oauth.business.oauth;


import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.self.oauth.persistence.entities.User;

public class TokenManagerTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("oauthTest");
	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@Test
	public void testGetAccessTokenValid() {
		final TokenManager tokenManager = new TokenManager(entityManager);
		final User user;
		{
			user = new User(UUID.randomUUID().toString(), "validAccessToken@gmail.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
			entityManager.persist(user);
			changeTransaction();
		}

		{
			// reusing logic for creating authCode
			final String authCode = UniqueIdGenerator.getAuthCode(user);
			final String authToken = tokenManager.getAccessToken(user.getClientId(), user.getClientSecret(), authCode);
			assertNotNull(authToken);
		}
	}

	@Test
	public void testGetAccessTokenInValid() {
		final TokenManager tokenManager = new TokenManager(entityManager);
		final User user;
		{
			user = new User(UUID.randomUUID().toString(), "validAccessTokenForInvalid@gmail.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
			entityManager.persist(user);
			changeTransaction();
		}

		{
			final User otherUser = new User(UUID.randomUUID().toString(), "inValidAccessToken@gmail.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
			// reusing logic for creating authCode
			final String authCode = UniqueIdGenerator.getAuthCode(otherUser);

			expectedException.expect(IllegalArgumentException.class);
			expectedException.expectMessage("information is not valid");
			tokenManager.getAccessToken(user.getClientId(), user.getClientSecret(), authCode);
		}
	}


	private void changeTransaction() {
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.getTransaction().begin();
	}

	@After
	public void tearDown() {
		entityManager.close();
	}
}