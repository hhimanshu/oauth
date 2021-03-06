package com.self.oauth.persistence.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("oauthTest");
	private UserService userService;
	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
		userService = new UserService(entityManager);
		entityManager.getTransaction().begin();
	}

	@Test
	public void testSaveUser() {
		final User user = new User(UUID.randomUUID().toString(), "testUser@email.com", "clientId", "clientSecret");
		final User persistedUser = userService.createUser(user);
		changeTransaction();

		assertNotNull(persistedUser.getId());
		assertTrue(persistedUser.isActive());
		assertEquals(user.getUserExternalId(), persistedUser.getUserExternalId());
		assertEquals(user.getEmail(), persistedUser.getEmail());
		assertEquals(user.getClientId(), persistedUser.getClientId());
		assertEquals(user.getClientSecret(), persistedUser.getClientSecret());
	}

	@Test
	public void testGetUserByEmail() {
		final String email = "testGetUserByEmail@email.com";
		{
			final User user = new User(UUID.randomUUID().toString(), email, UUID.randomUUID().toString(), UUID.randomUUID().toString());
			userService.createUser(user);
			changeTransaction();
		}
		{
			final User user = userService.getUserByEmail(email);
			assertNotNull(user);
			assertEquals(email, user.getEmail());
		}
	}

	@Test
	public void testGetUserByClientIdAndClientSecret() {
		final String email = "testGetUserByClientIdAndClientSecret@email.com";
		final User user = new User(UUID.randomUUID().toString(), email, UUID.randomUUID().toString(), UUID.randomUUID().toString());
		userService.createUser(user);
		changeTransaction();

		final User userInDb = userService.getUserByClientIdAndClientSecret(user.getClientId(), user.getClientSecret());
		assertNotNull(userInDb);
		assertEquals(email, userInDb.getEmail());
		assertEquals(user, userInDb);
	}

	private void changeTransaction() {
		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	@After
	public void tearDown() {
		entityManager.close();
	}
}
