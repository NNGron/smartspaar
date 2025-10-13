package com.smartspaar.pricecatalogservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

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
                .info(new Info().title("Price Catalog API").version("v1")
                        .description("Save and read prices in Postgres"));
    }
}