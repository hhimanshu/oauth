package com.self.oauth.business.oauth;

import java.util.UUID;

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
		if (!isValidAuthCode(authCode, clientId, clientSecret)) {
			throw new IllegalArgumentException("information is not valid");
		}
		return UUID.randomUUID().toString();
	}

	private boolean isValidAuthCode(@Nonnull final String authCode, @Nonnull final String clientId, @Nonnull final String clientSecret) {
		final UserService userService = new UserService(entityManager);
		final User existingUser;
		try {
			existingUser = userService.getUserByClientIdAndClientSecret(clientId, clientSecret);
		} catch (final NoResultException e) {
			return false;
		}

		final String authCodeGenerated = UniqueIdGenerator.getAuthCode(existingUser);
		return authCodeGenerated.equals(authCode);
	}
}
