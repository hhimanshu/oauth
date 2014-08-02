package com.self.services.oauth;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

public class ClientRequest {
    @NotNull(message = "email can not be empty")
    private final String email;
    @NotNull(message = "id can not be empty")
    private final long id;
    @NotNull(message = "password can not be empty")
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
