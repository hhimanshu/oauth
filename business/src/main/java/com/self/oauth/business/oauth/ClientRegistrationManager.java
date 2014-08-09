package com.self.oauth.business.oauth;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.self.oauth.business.outbound.ClientDetail;
import com.self.oauth.persistence.entities.services.OauthEntityManager;

@Stateless
public class ClientRegistrationManager {

	private EntityManager entityManager;

	public ClientRegistrationManager() {

	}

	@Inject
	public ClientRegistrationManager(@OauthEntityManager @Nonnull final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ClientDetail register(@Nonnull final String email, final long id, @Nonnull final String password) {
		return new ClientDetail("clientId", "clientSecret");
    }
}
