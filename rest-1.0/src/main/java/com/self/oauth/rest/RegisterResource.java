package com.self.oauth.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.OAuthServerRegistrationResponse;

@Path("/register")
public class RegisterResource {
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(@Context final HttpServletRequest request) throws OAuthSystemException {

        try {
            final OAuthServerRegistrationRequest oauthRequest = new OAuthServerRegistrationRequest(new JSONHttpServletRequestWrapper(request));
            oauthRequest.discover();
            oauthRequest.getClientName();
            oauthRequest.getClientUrl();
            oauthRequest.getClientDescription();
            oauthRequest.getRedirectURI();

            final OAuthResponse response = OAuthServerRegistrationResponse
                    .status(HttpServletResponse.SC_OK)
                    .setClientId(CommonExt.CLIENT_ID)
                    .setClientSecret(CommonExt.CLIENT_SECRET)
                    .setIssuedAt(CommonExt.ISSUED_AT)
                    .setExpiresIn(CommonExt.EXPIRES_IN)
                    .buildJSONMessage();
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();

        } catch (final OAuthProblemException e) {
            final OAuthResponse response = OAuthServerRegistrationResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .error(e)
                    .buildJSONMessage();
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        }
    }
}
