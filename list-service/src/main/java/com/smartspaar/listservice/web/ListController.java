package com.smartspaar.listservice.web;

import com.smartspaar.listservice.service.BackgroundFetchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * List controller.
 * It triggers price fetch when user adds item.
 */
@Tag(name = "Lists", description = "Manage lists and trigger fetch")
@RestController
@RequestMapping("/v1/lists")
public class ListController {

    private final BackgroundFetchService bg;

    public ListController(BackgroundFetchService bg) {
        this.bg = bg;
    }

    /**
     * Add item and start fetch.
     * @param id list id
     * @param name item name
     * @return simple message
     */
    @Operation(summary = "Add item and start fetch", description = "When item added, start background fetch")
    @PostMapping("/{id}/items")
    public String addItem(@PathVariable String id, @RequestParam String name) {
        // TODO: map "تخم مرغ" to "eieren" if needed
        String query = name;
        bg.startFetch(query);
        return "fetch started for query=" + query;
    }
}