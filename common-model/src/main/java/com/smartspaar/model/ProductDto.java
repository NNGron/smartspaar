package com.smartspaar.model;

/**
 * Product data in simple form.
 * name: product name
 * brand: product brand
 * quantity: like "12 stuks"
 * packaging: like "pack"
 * regularPrice: normal price
 * discountPrice: sale price
 * store: store code like "JUMBO"
 */
public record ProductDto(
        String name,
        String brand,
        String quantity,
        String packaging,
        Double regularPrice,
        Double discountPrice,
        String store
) {}