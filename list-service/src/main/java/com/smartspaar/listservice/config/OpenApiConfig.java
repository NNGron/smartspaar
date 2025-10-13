package com.smartspaar.listservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI config.
 * Set title and version.
 */
@Configuration
public class OpenApiConfig {
    /**
     * Build OpenAPI info.
     * @return OpenAPI bean
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("List service API").version("v1")
                        .description(""));
    }
}