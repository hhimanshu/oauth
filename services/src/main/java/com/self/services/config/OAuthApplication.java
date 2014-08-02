package com.self.services.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.self.services.oauth.RegisterResource;
import com.self.services.oauth.TokenResource;

@ApplicationPath("oauth")
public class OAuthApplication extends Application {
    final Set<Class<?>> classes = new HashSet<>();

    @Nonnull
    @Override
    public Set<Class<?>> getClasses() {
        classes.add(RegisterResource.class);
        classes.add(TokenResource.class);
        return Collections.unmodifiableSet(classes);
    }
}
