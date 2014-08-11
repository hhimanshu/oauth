package com.self.oauth.services.rest;

import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("protected")
public class ProtectedResource {
    @GET
    public Response helloRest() {
	    return Response.ok(Collections.singletonMap("data", "Hello Secured Client!")).build();
    }
}
