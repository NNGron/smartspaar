package com.smartspaar.pricecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Price catalog app.
 * It stores prices in DB.
 */
@SpringBootApplication
public class PriceCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCatalogServiceApplication.class, args);
	}

}
