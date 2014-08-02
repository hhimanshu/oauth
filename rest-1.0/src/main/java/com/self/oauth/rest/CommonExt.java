package com.self.oauth.rest;

public final class CommonExt {
    private CommonExt() {
    }

    public static final String REGISTRATION_ENDPOINT = "http://localhost:9000/auth/oauth2ext/register";
    public static final String APP_NAME = "Sample Application";
    public static final String APP_URL = "http://www.example.com";
    public static final String APP_ICON = "http://www.example.com/app.ico";
    public static final String APP_DESCRIPTION = "Description of a Sample App";
    public static final String APP_REDIRECT_URI = "http://www.example.com/redirect";

    public static final String CLIENT_ID = "someclientid";
    public static final String CLIENT_SECRET = "someclientsecret";
    public static final String ISSUED_AT = "0123456789";
    public static final Long EXPIRES_IN = 987654321l;

}
