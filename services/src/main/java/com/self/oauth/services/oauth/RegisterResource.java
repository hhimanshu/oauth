package com.self.oauth.services.oauth;

import javax.annotation.Nonnull;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.self.oauth.business.oauth.ClientRegistrationManager;
import com.self.oauth.business.outbound.ClientDetail;

@RequestScoped
@Path("register")
public class RegisterResource {
	private ClientRegistrationManager clientRegistrationManager;

	@SuppressWarnings("UnusedDeclaration")
	public RegisterResource() {
		// used by jax-rs
	}

	@Inject
	public RegisterResource(@Nonnull final ClientRegistrationManager clientRegistrationManager) {
		this.clientRegistrationManager = clientRegistrationManager;
	}

	@GET
	public Response helloOauth() {
		return Response.ok("Hello Oauth").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerClient(@Nonnull final ClientRequest clientRequest) {
		final ClientDetail clientDetail = clientRegistrationManager.register(clientRequest.getEmail(), clientRequest.getUserExternalId(), clientRequest.getPassword());
		return Response.ok(clientDetail).build();
	}
}
