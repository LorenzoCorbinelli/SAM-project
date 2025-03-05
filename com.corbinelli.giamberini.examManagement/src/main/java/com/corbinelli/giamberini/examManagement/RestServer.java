package com.corbinelli.giamberini.examManagement;

import java.net.URI;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestServer {
    private static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) {
        System.out.println("Starting Grizzly HTTP server...");

        // Register your JAX-RS resources and JSON-B feature
        final ResourceConfig rc = new ResourceConfig()
            .packages("com.corbinelli.giamberini.examManagement.resources")
            .register(JsonBindingFeature.class);  // Register JSON-B for JSON serialization

        // Start the server
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        System.out.println("JAX-RS app running at: " + BASE_URI);
        System.out.println("Press Ctrl + C to stop...");
    }
}
