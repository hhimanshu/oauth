package com.self.business.outbound;

import javax.annotation.Nonnull;

public class ClientDetail {
    private final String clientId;
    private final String clientSecret;

    public ClientDetail(@Nonnull final String clientId, @Nonnull final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Nonnull
    public String getClientId() {
        return clientId;
    }

    @Nonnull
    public String getClientSecret() {
        return clientSecret;
    }
}
