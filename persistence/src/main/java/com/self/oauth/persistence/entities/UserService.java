package com.self.oauth.persistence.entities;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class UserService {
    private final EntityManager entityManager;

    @Inject
    public UserService(@Nonnull final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Nonnull
    public User createUser(@Nonnull final User user) {
        entityManager.persist(user);
        return user;
    }
}