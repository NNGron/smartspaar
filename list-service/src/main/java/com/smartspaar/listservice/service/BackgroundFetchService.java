package com.smartspaar.listservice.service;

import com.smartspaar.listservice.client.AggregatorClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Background fetch.
 * It calls aggregator in background.
 */
@Service
public class BackgroundFetchService {

    private final AggregatorClient agg;

    public BackgroundFetchService(AggregatorClient agg) {
        this.agg = agg;
    }

    /**
     * Start fetch.
     * @param query search text
     */
    @Async
    public void startFetch(String query) {
        try {
            agg.fetch(query);
        } catch (Exception ignored) {
            // log error if needed
        }
    }
}