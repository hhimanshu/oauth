package com.self.oauth.business.oauth;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;

@Stateless
public class TokenManager {
    public String getAccessToken(@Nonnull final String clientId, @Nonnull final String clientSecret) {
        return UUID.randomUUID().toString();
    }
}
