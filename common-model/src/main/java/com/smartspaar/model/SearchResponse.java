package com.smartspaar.model;

import java.util.List;

/**
 * Search result list.
 * items: product list
 * cached: from cache or not
 * lastUpdated: epoch millis
 */
public record SearchResponse(
        List<ProductDto> items,
        boolean cached,
        long lastUpdated
) {}