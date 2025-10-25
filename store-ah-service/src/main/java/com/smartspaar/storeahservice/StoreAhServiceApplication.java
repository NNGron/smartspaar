package com.smartspaar.storeahservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Store AH Service.
 * Fetches products from Albert Heijn.
 */
@SpringBootApplication
public class StoreAhServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreAhServiceApplication.class, args);
    }
}