package org.acme.microprofile;

import io.smallrye.health.checks.UrlHealthCheck;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;

@ApplicationScoped  
public class HealthChecker {
// curl -w '\n' localhost:8080/health/ready
    @ConfigProperty(name = "quarkus.rest-client.\"com.redhat.developers.SwapiService\".url")
    String externalURL;

    @Readiness 
    HealthCheck checkURL() {
        return new UrlHealthCheck(externalURL+"/api/films/") 
                .name("ExternalURL health check").requestMethod(HttpMethod.GET).statusCode(200);
    }
}