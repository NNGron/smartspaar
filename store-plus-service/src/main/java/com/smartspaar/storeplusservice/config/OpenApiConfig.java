package com.smartspaar.storeplusservice.config;

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
                        .title("Store Plus Service API")
                        .version("v1")
                        .description("Search products in Plus. Get name, brand, quantity, prices. Query example: 'eieren'.")
        );
    }
}