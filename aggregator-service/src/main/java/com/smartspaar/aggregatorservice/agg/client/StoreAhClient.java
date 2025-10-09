package com.smartspaar.aggregatorservice.agg.client;

import com.smartspaar.model.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Call AH store.
 * Return search result.
 */
@FeignClient(name = "storeAh", url = "${stores.ah.url}")
public interface StoreAhClient {
    /** Search products by query. */
    @GetMapping("/v1/search")
    SearchResponse search(@RequestParam String query);
}