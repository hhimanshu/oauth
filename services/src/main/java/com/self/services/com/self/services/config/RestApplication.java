package com.self.services.com.self.services.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.self.services.rest.ProtectedResource;

@ApplicationPath("rest")
public class RestApplication extends Application {
    final Set<Class<?>> classes = new HashSet<>();

    @Nonnull
    @Override
    public Set<Class<?>> getClasses() {
        classes.add(ProtectedResource.class);
        return Collections.unmodifiableSet(classes);
    }
}
