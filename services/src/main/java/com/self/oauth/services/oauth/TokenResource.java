package com.self.oauth.services.oauth;

import java.util.Collections;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.self.oauth.business.oauth.TokenManager;

@Path("token")
public class TokenResource {
	private TokenManager tokenManager;

	@SuppressWarnings("UnusedDeclaration")
	public TokenResource() {
		// used by jax-rs
	}

	@Inject
	public TokenResource(@Nonnull final TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthToken(@Nonnull final TokenRequest tokenRequest) {
		final String authToken;
		try {
			authToken = tokenManager.getAccessToken(tokenRequest.getClientId(), tokenRequest.getClientSecret(), tokenRequest.getAuthCode());
		} catch (final Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", e.getMessage())).build();
		}
		return Response.ok(Collections.singletonMap("authToken", authToken)).build();
	}
}
