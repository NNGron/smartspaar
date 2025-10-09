package com.smartspaar.aggregatorservice.agg.client;

import com.smartspaar.model.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Call Lidl store.
 * Return search result.
 */
@FeignClient(name = "storeLidl", url = "${stores.lidl.url}")
public interface StoreLidlClient {
    /** Search products by query. */
    @GetMapping("/v1/search")
    SearchResponse search(@RequestParam String query);
}