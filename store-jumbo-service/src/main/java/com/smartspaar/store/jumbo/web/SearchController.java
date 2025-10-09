package com.smartspaar.store.jumbo.web;

import com.smartspaar.model.ProductDto;
import com.smartspaar.model.SearchResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * Search endpoint for Jumbo.
 * It returns a simple product list.
 */
@RestController
@RequestMapping("/v1/search")
public class SearchController {

    /**
     * Search products by query.
     * TODO: implement real Jsoup.
     */
    @GetMapping
    public SearchResponse search(@RequestParam String query) {
        // Simple demo data
        List<ProductDto> items = List.of(
                new ProductDto("Eieren 12 stuks", "Jumbo", "12 stuks", "pack", 4.79, null, "JUMBO"),
                new ProductDto("Eieren 10 stuks", "Jumbo", "10 stuks", "pack", 4.19, null, "JUMBO")
        );
        return new SearchResponse(items, false, Instant.now().toEpochMilli());
    }
}