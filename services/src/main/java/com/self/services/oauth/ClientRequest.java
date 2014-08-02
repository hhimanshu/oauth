package com.self.services.oauth;

import javax.annotation.Nonnull;

public class ClientRequest {
    private final String email;
    private final long id;
    private final String password;

    public ClientRequest(@Nonnull final String email, final long id, @Nonnull final String password) {
        this.email = email;
        this.id = id;
        this.password = password;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
