package com.self.oauth.persistence.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("oauthTest");
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(entityManagerFactory.createEntityManager());
    }

    @Test
    public void testUser() {
        final User user = new User(UUID.randomUUID().toString(), "testUser@email.com", "clientId", "clientSecret");
        final User persistedUser = userService.createUser(user);

        assertNotNull(persistedUser.getId());
        assertTrue(persistedUser.isActive());
        assertEquals(user.getExternalUserId(), persistedUser.getExternalUserId());
        assertEquals(user.getEmail(), persistedUser.getEmail());
        assertEquals(user.getClientId(), persistedUser.getClientId());
        assertEquals(user.getClientSecret(), persistedUser.getClientSecret());
    }
}
