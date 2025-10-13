package com.smartspaar.aggregatorservice.agg.client;

import com.smartspaar.model.PriceBulkRequest;
import com.smartspaar.model.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Call price-catalog service.
 * Save and read prices.
 */
@FeignClient(
        name = "price-catalog",
        contextId = "priceCatalogClient",
        url = "${catalog.url}"
)
public interface PriceCatalogClient {
    /** Save bulk prices. */
    @PostMapping("/v1/prices/bulk")
    int bulk(@RequestBody PriceBulkRequest req);

    /** Read prices by query. */
    @GetMapping("/v1/prices")
    SearchResponse read(@RequestParam String query);
}