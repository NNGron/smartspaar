package com.smartspaar.listservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * List service app.
 * It triggers background fetch.
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.smartspaar.listservice.client")
@EnableAsync
public class ListServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ListServiceApplication.class, args);
    }
}