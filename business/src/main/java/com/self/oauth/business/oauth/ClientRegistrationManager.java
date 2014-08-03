package com.self.oauth.business.oauth;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;

import com.self.oauth.business.outbound.ClientDetail;

@Stateless
public class ClientRegistrationManager {
    public ClientDetail register(@Nonnull final String email, final long id, @Nonnull final String password) {
        return new ClientDetail("clientId", "clientSecret");
    }
}
