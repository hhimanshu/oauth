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

/*
    @Nullable
    public User getUserByEmail(final String email) {
        final JPQLQuery query = new JPAQuery(entityManager);
        return query.from(QUser.user).where(QUser.user.email.eq(email)).fetch().uniqueResult(QUser.user);
    }
*/
}
