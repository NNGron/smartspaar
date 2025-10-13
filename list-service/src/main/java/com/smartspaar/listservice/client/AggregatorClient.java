package com.smartspaar.listservice.client;

import com.smartspaar.model.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Call aggregator fetch API.
 * It starts background fetch.
 */
@FeignClient(name = "aggregator", url = "${aggregator.url}")
public interface AggregatorClient {
    /** Call fetch endpoint. */
    @PostMapping("/v1/fetch")
    SearchResponse fetch(@RequestParam String query);
}