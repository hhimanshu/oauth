package com.self.oauth.persistence.entities;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

public class UserService {
    private final EntityManager entityManager;

    public UserService(@Nonnull final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Nonnull
    public User createUser(@Nonnull final User user) {
        entityManager.persist(user);
        return user;
    }

	@Nonnull
	public User getUserByEmail(final String email) {
	    return (User) entityManager.createQuery("select user from User user where user.email like :email")
			    .setParameter("email", email).getSingleResult();
    }

	@Nonnull
	public User getUserByClientIdAndClientSecret(@Nonnull final String clientId, @Nonnull final String clientSecret) {
		return (User) entityManager.createQuery("select user from User user where user.clientId like :clientId and user.clientSecret like :clientSecret")
				.setParameter("clientId", clientId)
				.setParameter("clientSecret", clientSecret).getSingleResult();
	}
}
