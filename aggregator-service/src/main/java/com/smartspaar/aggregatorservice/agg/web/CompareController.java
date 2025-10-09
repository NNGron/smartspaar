package com.smartspaar.aggregatorservice.agg.web;

import com.smartspaar.aggregatorservice.agg.client.StoreAhClient;
import com.smartspaar.aggregatorservice.agg.client.StoreJumboClient;
import com.smartspaar.aggregatorservice.agg.client.StoreLidlClient;
import com.smartspaar.model.ProductDto;
import com.smartspaar.model.SearchResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Compare products.
 * Merge stores and sort by price.
 */
@RestController
@RequestMapping("/v1/compare")
public class CompareController {

    private final StoreJumboClient jumbo;
    private final StoreAhClient ah;
    private final StoreLidlClient lidl;
    private final RedisTemplate<String, SearchResponse> redis;

    public CompareController(StoreJumboClient jumbo,
                             StoreAhClient ah,
                             StoreLidlClient lidl,
                             RedisTemplate<String, SearchResponse> redis) {
        this.jumbo = jumbo;
        this.ah = ah;
        this.lidl = lidl;
        this.redis = redis;
    }

    /** Compare by query. Use cache first. */
    @GetMapping
    public SearchResponse compare(@RequestParam String query) {
        String key = "cmp:" + query;
        SearchResponse c = redis.opsForValue().get(key);
        if (c != null) return c;

        List<ProductDto> all = new ArrayList<>();
        try { all.addAll(jumbo.search(query).items()); } catch (Exception ignored) {}
        try { all.addAll(ah.search(query).items()); } catch (Exception ignored) {}
        try { all.addAll(lidl.search(query).items()); } catch (Exception ignored) {}

        all.sort(Comparator.comparingDouble(this::priceNow));
        SearchResponse resp = new SearchResponse(all, false, Instant.now().toEpochMilli());
        redis.opsForValue().set(key, resp);
        redis.expire(key, java.time.Duration.ofHours(2));
        return resp;
    }

    /** Get final price. */
    private double priceNow(ProductDto p) {
        Double d = p.discountPrice();
        Double r = p.regularPrice();
        if (d != null) return d;
        if (r != null) return r;
        return Double.MAX_VALUE;
    }
}