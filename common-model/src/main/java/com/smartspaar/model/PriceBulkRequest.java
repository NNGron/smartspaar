package com.smartspaar.model;

import java.util.List;

/**
 * Bulk price request.
 * query: search text
 * items: products to save
 * fetchedAt: time in ms
 */
public record PriceBulkRequest(
        String query,
        List<ProductDto> items,
        long fetchedAt
) {}