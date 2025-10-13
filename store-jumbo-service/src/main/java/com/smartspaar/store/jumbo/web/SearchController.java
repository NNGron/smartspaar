package com.smartspaar.store.jumbo.web;

import com.smartspaar.model.ProductDto;
import com.smartspaar.model.SearchResponse;
import com.smartspaar.store.jumbo.service.JumboScrapeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * Search endpoint for Jumbo.
 * It uses Jsoup now.
 */
@Tag(name = "Search", description = "Find products in Jumbo")
@RestController
@RequestMapping("/v1/search")
public class SearchController {

    private final JumboScrapeService svc;

    public SearchController(JumboScrapeService svc) {
        this.svc = svc;
    }

    /**
     * Search products by text.
     * @param query search text
     * @return SearchResponse list
     */
    @Operation(summary = "Search products", description = "Get product list from Jumbo by Jsoup")
    @GetMapping
    public SearchResponse search(@RequestParam String query) throws Exception {
        List<ProductDto> items = svc.search(query);
        return new SearchResponse(items, false, Instant.now().toEpochMilli());
    }
}

/**
 *
 *      * Search products by query.
 *      * TODO: implement real Jsoup.
 *
 *    @GetMapping
 *     public SearchResponse search(@RequestParam String query) {
 *         // Simple demo data
 *         List<ProductDto> items = List.of(
 *                 new ProductDto("Eieren 12 stuks", "Jumbo", "12 stuks", "pack", 4.79, null, "JUMBO"),
 *                 new ProductDto("Eieren 10 stuks", "Jumbo", "10 stuks", "pack", 4.19, null, "JUMBO")
 *         );
 *         return new SearchResponse(items, false, Instant.now().toEpochMilli());
 *     }
 */