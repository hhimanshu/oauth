package com.self.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("protected")
public class ProtectedResource {
    @GET
    public Response helloRest() {
        return Response.ok("Hello REST").build();
    }
}
