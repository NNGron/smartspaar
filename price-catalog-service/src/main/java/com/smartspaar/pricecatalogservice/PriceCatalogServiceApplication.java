package com.smartspaar.pricecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Price catalog app.
 * Stores prices in DB.
 */
@SpringBootApplication
@EntityScan(basePackages = "com.smartspaar.pricecatalogservice.model")
public class PriceCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCatalogServiceApplication.class, args);
	}
}