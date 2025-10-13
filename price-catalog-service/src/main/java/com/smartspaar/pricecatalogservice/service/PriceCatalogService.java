package com.smartspaar.pricecatalogservice.service;

import com.smartspaar.model.ProductDto;
import com.smartspaar.pricecatalogservice.model.PriceEntity;
import com.smartspaar.pricecatalogservice.repository.PriceRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Catalog service.
 * It saves and reads prices.
 */
@Service
public class PriceCatalogService {

    private final PriceRepository repo;

    public PriceCatalogService(PriceRepository repo) {
        this.repo = repo;
    }

    /**
     * Save products as prices.
     * @param query search text
     * @param items product list
     * @return saved count
     */
    public int saveBulk(String query, List<ProductDto> items) {
        List<PriceEntity> list = new ArrayList<>();
        Instant now = Instant.now();
        for (ProductDto p : items) {
            PriceEntity e = toEntity(query, p, now);
            list.add(e);
        }
        repo.saveAll(list);
        return list.size();
    }

    /**
     * Read prices by query.
     * @param query search text
     * @return price list
     */
    public List<PriceEntity> findByQuery(String query) {
        return repo.findByQueryIgnoreCase(query);
    }

    private PriceEntity toEntity(String query, ProductDto p, Instant now) {
        PriceEntity e = new PriceEntity();
        e.setStore(p.store());
        e.setQuery(query);
        e.setName(p.name());
        e.setBrand(p.brand());
        e.setQuantity(p.quantity());
        e.setPackaging(p.packaging());
        e.setRegularPrice(p.regularPrice());
        e.setDiscountPrice(p.discountPrice());
        e.setCurrency("EUR");
        e.setUnitPrice(null);
        e.setSourceType("API"); // TODO: set real source type
        e.setConfidence(90);
        e.setLastUpdated(now);
        return e;
    }
}