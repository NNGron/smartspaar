package com.smartspaar.storeahservice.web;

import com.smartspaar.model.ProductDto;
import com.smartspaar.model.SearchResponse;
import com.smartspaar.storeahservice.service.AhScrapeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * Search endpoint for AH.
 * It uses Jsoup now.
 */
@Tag(name = "Search", description = "Find products in Albert Heijn")
@RestController
@RequestMapping("/v1/search")
public class AhController {

    private final AhScrapeService svc;

    public AhController(AhScrapeService svc) {
        this.svc = svc;
    }

    /**
     * Search products by text.
     * @param query search text
     * @return SearchResponse list
     */
    @Operation(summary = "Search products", description = "Get product list from AH by Jsoup")
    @GetMapping
    public SearchResponse search(@RequestParam String query) throws Exception {
        List<ProductDto> items = svc.search(query);
        return new SearchResponse(items, false, Instant.now().toEpochMilli());
    }
}
