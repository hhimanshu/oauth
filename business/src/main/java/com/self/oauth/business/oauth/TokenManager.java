package com.self.oauth.business.oauth;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.self.oauth.persistence.entities.User;
import com.self.oauth.persistence.entities.UserService;
import com.self.oauth.persistence.entities.services.OauthEntityManager;

@Stateless
public class TokenManager {

	private EntityManager entityManager;

	@SuppressWarnings("UnusedDeclaration")
	public TokenManager() {
	}

	@Inject
	public TokenManager(@OauthEntityManager @Nonnull final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Nonnull
	public String getAccessToken(@Nonnull final String clientId, @Nonnull final String clientSecret, @Nonnull final String authCode) {
		final User existingUser;
		try {
			existingUser = new UserService(entityManager).getUserByClientIdAndClientSecret(clientId, clientSecret);
		} catch (final NoResultException e) {
			throw new IllegalArgumentException("No user exists with given clientId and clientSecret");
		}

		if (!UniqueIdGenerator.getAuthCode(existingUser).equals(authCode)) {
			throw new IllegalArgumentException("information is not valid");
		}
		return new UniqueIdGenerator(ClientRegistrationManager.SERVER_PRIVATE_KEY).getAuthToken(existingUser);
	}
}
