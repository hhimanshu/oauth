package com.self.oauth.persistence.entities;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private final String id;
    private final String externalUserId;
    private final String email;
    private final String clientId;
    private final String clientSecret;
    private final boolean active;

    public User(@Nonnull final String externalUserId, @Nonnull final String email,
                @Nonnull final String clientId, @Nonnull final String clientSecret, final boolean active) {
        id = UUID.randomUUID().toString();
        this.externalUserId = externalUserId;
        this.email = email;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.active = active;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getExternalUserId() {
        return externalUserId;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public String getClientId() {
        return clientId;
    }

    @Nonnull
    public String getClientSecret() {
        return clientSecret;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", externalUserId='" + externalUserId + '\'' +
                ", email='" + email + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", active=" + active +
                '}';
    }
}
