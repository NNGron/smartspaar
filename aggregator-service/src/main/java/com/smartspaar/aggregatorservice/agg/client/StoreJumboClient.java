package com.smartspaar.aggregatorservice.agg.client;

import com.smartspaar.model.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Call Jumbo store.
 * Return search result.
 */
@FeignClient(
        name = "store-jumbo",              // unique bean name
        contextId = "storeJumboClient",    // unique context id
        url = "${stores.jumbo.url}"
)
public interface StoreJumboClient {
    /** Search products by query. */
    @GetMapping("/v1/search")
    SearchResponse search(@RequestParam String query);
}