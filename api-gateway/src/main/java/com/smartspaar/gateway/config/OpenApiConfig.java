package com.smartspaar.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI config.
 * Sets title and version.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Build OpenAPI info.
     * @return OpenAPI bean
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info()
                        .title("API Gateway")
                        .version("v1")
                        .description("")
        );
    }
}