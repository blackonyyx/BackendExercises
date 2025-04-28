package org.acme;

import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/hello/{name}")
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @GET
    public String params(
            @RestPath("/{name}") String name,
            @RestQuery("suffix") String suffix
        ) {
        if (suffix != null && !suffix.isEmpty()) {
            return greetingService.greet(name, suffix);
        } else {
            return greetingService.greet(name);
        }
    }
}