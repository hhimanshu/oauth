package com.self.oauth.business.oauth;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.self.oauth.business.outbound.ClientDetail;

public class ClientRegistrationManagerTest {
	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("oauthTest");
	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@Test
	public void testRegisterNewClient() {
		final ClientRegistrationManager clientRegistrationManager = new ClientRegistrationManager(entityManager);
		final ClientDetail clientDetail = clientRegistrationManager.register("uniqueEmail@gmail.com", "userExternalId", "password");

		assertNotNull(clientDetail.getClientId());
		assertNotNull(clientDetail.getClientSecret());
	}

	@Test
	public void testRegisterAlreadyRegisteredClient() {
		final ClientRegistrationManager clientRegistrationManager = new ClientRegistrationManager(entityManager);

		final ClientDetail clientDetail1 = clientRegistrationManager.register("uniqueEmail@gmail.com", "userExternalId", "password");
		changeTransaction();

		final ClientDetail clientDetail2 = clientRegistrationManager.register("uniqueEmail@gmail.com", "userExternalId", "password");
		changeTransaction();

		assertNotNull(clientDetail1.getClientId());
		assertNotNull(clientDetail1.getClientSecret());

		assertNotNull(clientDetail2.getClientId());
		assertNotNull(clientDetail2.getClientSecret());

		assertEquals(clientDetail1.getClientId(), clientDetail2.getClientId());
		assertEquals(clientDetail1.getClientSecret(), clientDetail2.getClientSecret());
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