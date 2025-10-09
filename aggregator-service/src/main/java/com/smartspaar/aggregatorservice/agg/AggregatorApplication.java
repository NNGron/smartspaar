package com.smartspaar.aggregatorservice.agg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Aggregator app.
 * It calls store services in parallel.
 */
@SpringBootApplication
@EnableFeignClients
public class AggregatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }
}