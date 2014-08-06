package com.self.oauth.persistence.entities;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String id;
    private String externalUserId;
    private String email;
    private String clientId;
    private String clientSecret;
    private boolean active;

    public User(@Nonnull final String externalUserId, @Nonnull final String email,
                @Nonnull final String clientId, @Nonnull final String clientSecret) {
        id = UUID.randomUUID().toString();
        this.externalUserId = externalUserId;
        this.email = email;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        active = true;
    }

    public User() {
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
