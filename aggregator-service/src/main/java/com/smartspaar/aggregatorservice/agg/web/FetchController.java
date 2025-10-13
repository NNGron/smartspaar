package com.smartspaar.aggregatorservice.agg.web;

import com.smartspaar.aggregatorservice.agg.client.PriceCatalogClient;
import com.smartspaar.aggregatorservice.agg.client.StoreJumboClient;
import com.smartspaar.model.PriceBulkRequest;
import com.smartspaar.model.ProductDto;
import com.smartspaar.model.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Fetch controller.
 * It fetches from stores and saves.
 */
@Tag(name = "Fetch", description = "Fetch prices in background")
@RestController
@RequestMapping("/v1/fetch")
public class FetchController {

    private final StoreJumboClient jumbo;
    private final PriceCatalogClient catalog;
    private final RedisTemplate<String, SearchResponse> redis;

    public FetchController(StoreJumboClient jumbo, PriceCatalogClient catalog, RedisTemplate<String, SearchResponse> redis) {
        this.jumbo = jumbo;
        this.catalog = catalog;
        this.redis = redis;
    }

    /**
     * Fetch and save prices.
     * @param query search text
     * @return SearchResponse merged
     */
    @Operation(summary = "Fetch prices", description = "Fetch from stores, save to catalog, cache response")
    @PostMapping
    public SearchResponse fetch(@RequestParam String query) {
        List<ProductDto> all = new ArrayList<>();

        try {
            var r = jumbo.search(query);
            if (r != null && r.items() != null) all.addAll(r.items());
        } catch (Exception ignored) {}

        // TODO: add other stores here (AH, Lidl, ...)

        // Save to catalog
        catalog.bulk(new PriceBulkRequest(query, all, Instant.now().toEpochMilli()));

        // Sort and cache
        all.sort(Comparator.comparingDouble(this::priceNow));
        SearchResponse resp = new SearchResponse(all, false, System.currentTimeMillis());
        String key = "cmp:" + query;
        redis.opsForValue().set(key, resp);
        redis.expire(key, java.time.Duration.ofHours(2));
        return resp;
    }

    /** Get effective price. */
    private double priceNow(ProductDto p) {
        Double d = p.discountPrice();
        Double r = p.regularPrice();
        if (d != null) return d;
        if (r != null) return r;
        return Double.MAX_VALUE;
    }
}