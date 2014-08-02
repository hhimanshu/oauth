package com.self.services.oauth;

import javax.annotation.Nonnull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("register")
public class RegisterResource {
    @GET
    public Response helloOauth() {
        return Response.ok("Hello Oauth").build();
    }

    @POST
    public Response registerClient(@Nonnull final ClientRequest clientRequest) {
        return Response.ok().build();
    }
}
