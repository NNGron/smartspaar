//package com.smartspaar.storeahservice.web;
//
//import com.smartspaar.model.ProductDto;
//import com.smartspaar.model.SearchResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import org.springframework.web.bind.annotation.*;
//
///**
//
// Search endpoint for AH.
//
// It returns products by query.
// */
//@Tag(name = "Search", description = "Find products in AH")
//@RestController
//@RequestMapping("/v1/search")
//public class SearchController {
//
//    /**
//
//     Search products by text.
//     @param query search text
//     @return SearchResponse with items
//     */
//    @Operation(
//            summary = "Search products",
//            description = "Search products in AH by text. Example query: 'eieren'.",
//            parameters = {
//                    @Parameter(name = "query", description = "Search text", example = "eieren", required = true)
//            },
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "OK",
//                            content = @Content(
//                                    schema = @Schema(implementation = SearchResponse.class),
//                                    examples = @ExampleObject(
//                                            name = "sample",
//                                            value = """
//{
//"items": [
//{"name":"Eieren 12 stuks","brand":"AH","quantity":"12 stuks","packaging":"pack","regularPrice":2.99,"discountPrice":2.49,"store":"AH"}
//],
//"cached": false,
//"lastUpdated": 1710000000000
//}
//"""
//                                    )
//                            )
//                    ),
//                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
//            }
//    )
//    @GetMapping
//    public SearchResponse search(@RequestParam String query) {
//// TODO: real fetch (JSON/HTML). Now demo or call service layer.
//        return new SearchResponse(
//                java.util.List.of(
//                        new ProductDto("Eieren 12 stuks", "AH", "12 stuks", "pack", 2.99, 2.49, "AH")
//                ),
//                false,
//                System.currentTimeMillis()
//        );
//    }
//}
//
