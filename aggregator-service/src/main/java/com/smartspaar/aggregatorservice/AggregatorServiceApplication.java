package com.smartspaar.aggregatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Aggregator app.
 * It starts REST server.
 * It scans Feign clients.
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.smartspaar.aggregatorservice.agg.client")
public class AggregatorServiceApplication {

	/**
	 * Start application.
	 * This is main method.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AggregatorServiceApplication.class, args);
	}
}