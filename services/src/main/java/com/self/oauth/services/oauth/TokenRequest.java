package com.self.oauth.services.oauth;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

public class TokenRequest {
	@NotNull(message = "clientId can not be empty")
	private String clientId;
	@NotNull(message = "authCode can not be empty")
	private String clientSecret;
	@NotNull(message = "authCode can not be empty")
	private String authCode;


	@SuppressWarnings("UnusedDeclaration")
	public TokenRequest() {
		// used by jax-rs
	}

	public TokenRequest(@Nonnull final String clientId, @Nonnull final String clientSecret, @Nonnull final String authCode) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.authCode = authCode;
	}

	@Nonnull
	public String getClientId() {
		return clientId;
	}

	@Nonnull
	public String getClientSecret() {
		return clientSecret;
	}

	@Nonnull
	public String getAuthCode() {
		return authCode;
	}

	@Override
	public String toString() {
		return "TokenRequest{" +
				"clientId='" + clientId + '\'' +
				", clientSecret='" + clientSecret + '\'' +
				", authCode='" + authCode + '\'' +
				'}';
	}
}
