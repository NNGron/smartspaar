package com.smartspaar.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Print Swagger links on startup.
 * It also prints app name.
 */
@Component
public class SwaggerLinksLogger {

    /** App name from config. */
    @Value("${spring.application.name:app}")
    private String appName;

    /** HTTP port. */
    @Value("${server.port:8080}")
    private int port;

    /** Context path (optional). */
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    /** Swagger UI path. */
    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerUiPath;

    /** OpenAPI docs path. */
    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocsPath;

    /**
     * Print links when app is ready.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void printLinks() {
        String base = "http://localhost:" + port + (contextPath == null ? "" : contextPath);
        System.out.println("\n==== Swagger (" + appName + ") ====");
        System.out.println("UI : " + base + swaggerUiPath);
        System.out.println("API: " + base + apiDocsPath + "\n");
    }
}