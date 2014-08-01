package com.self.oauth.rest.configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.self.oauth.rest.HelloResource;
import com.self.oauth.rest.OAuthRSResponse;
import com.self.oauth.rest.ProtectedResource;
import com.self.oauth.rest.RegisterResource;
import com.self.oauth.rest.TokenResource;

@ApplicationPath("rest")
public class RestApplication extends Application {
    final Set<Class<?>> resourceClasses = new HashSet<>();

    @Override
    public Set<Class<?>> getClasses() {
        resourceClasses.add(HelloResource.class);
        resourceClasses.add(OAuthRSResponse.class);
        resourceClasses.add(ProtectedResource.class);
        resourceClasses.add(RegisterResource.class);
        resourceClasses.add(TokenResource.class);

        return Collections.unmodifiableSet(resourceClasses);
    }
}
