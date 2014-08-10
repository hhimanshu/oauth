package com.self.oauth.services.oauth;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

public class ClientRequest {
	@NotNull(message = "email can not be empty")
	private String email;
	@NotNull(message = "userExternalId can not be empty")
	private String userExternalId;
	@NotNull(message = "password can not be empty")
	private String password;


	@SuppressWarnings("UnusedDeclaration")
	public ClientRequest() {
		// used by jax-rs
	}

	public ClientRequest(@Nonnull final String email, @Nonnull final String userExternalId, @Nonnull final String password) {
		this.email = email;
		this.userExternalId = userExternalId;
		this.password = password;
	}

	@Nonnull
	public String getEmail() {
		return email;
	}

	@Nonnull
	public String getUserExternalId() {
		return userExternalId;
	}

	@Nonnull
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "ClientRequest{" +
				"email='" + email + '\'' +
				", userExternalId='" + userExternalId + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
