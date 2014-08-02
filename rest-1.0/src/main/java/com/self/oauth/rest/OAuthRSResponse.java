package com.self.oauth.rest;


import org.apache.oltu.oauth2.common.message.OAuthResponse;

public class OAuthRSResponse extends OAuthResponse {

    protected OAuthRSResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }

    public static class OAuthRSResponseBuilder extends OAuthResponse.OAuthResponseBuilder {

        public OAuthRSResponseBuilder(int responseCode) {
            super(responseCode);
        }
    }
}
