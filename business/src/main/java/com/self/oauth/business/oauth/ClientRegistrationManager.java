package com.self.oauth.business.oauth;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.self.oauth.business.outbound.ClientDetail;
import com.self.oauth.persistence.entities.User;
import com.self.oauth.persistence.entities.UserService;
import com.self.oauth.persistence.entities.services.OauthEntityManager;

@Stateless
public class ClientRegistrationManager {
	private EntityManager entityManager;

	@SuppressWarnings("UnusedDeclaration")
	public ClientRegistrationManager() {
	}

	@Inject
	public ClientRegistrationManager(@Nonnull @OauthEntityManager final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ClientDetail register(@Nonnull final String email, @Nonnull final String userExternalId, @Nonnull final String password) {
		final UserService userService = new UserService(entityManager);
		final User existingUser = userService.getUserByEmail(email);
		if (existingUser != null) {
			return new ClientDetail(existingUser.getClientId(), existingUser.getClientSecret());
		}

		final UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator("secure key");
		final String clientId = uniqueIdGenerator.getClientId(email, userExternalId);
		final String clientSecret = uniqueIdGenerator.getClientSecret(clientId);
		final User newUser = userService.createUser(new User(userExternalId, email, clientId, clientSecret));
		return new ClientDetail(newUser.getClientId(), newUser.getClientSecret());
	}
}
