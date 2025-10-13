package com.smartspaar.pricecatalogservice.web;

import com.smartspaar.model.PriceBulkRequest;
import com.smartspaar.model.ProductDto;
import com.smartspaar.model.SearchResponse;
import com.smartspaar.pricecatalogservice.model.PriceEntity;
import com.smartspaar.pricecatalogservice.service.PriceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Price catalog API.
 * Save and read prices.
 */
@Tag(name = "Prices", description = "Save and read prices")
@RestController
@RequestMapping("/v1/prices")
public class PriceCatalogController {

    private final PriceCatalogService svc;

    public PriceCatalogController(PriceCatalogService svc) {
        this.svc = svc;
    }

    /**
     * Save bulk prices.
     * @param req bulk request
     * @return saved count
     */
    @Operation(summary = "Save bulk prices", description = "Save list of products with query")
    @PostMapping("/bulk")
    public int bulk(@RequestBody PriceBulkRequest req) {
        return svc.saveBulk(req.query(), req.items());
    }

    /**
     * Read prices by query.
     * @param query search text
     * @return mapped products
     */
    @Operation(summary = "Read prices", description = "Read saved prices by query")
    @GetMapping
    public SearchResponse read(@RequestParam String query) {
        List<PriceEntity> list = svc.findByQuery(query);
        List<ProductDto> items = list.stream().map(e ->
                new ProductDto(
                        e.getName(), e.getBrand(), e.getQuantity(), e.getPackaging(),
                        e.getRegularPrice(), e.getDiscountPrice(), e.getStore()
                )
        ).toList();
        return new SearchResponse(items, true, System.currentTimeMillis());
    }
}