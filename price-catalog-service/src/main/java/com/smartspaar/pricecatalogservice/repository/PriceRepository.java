package com.smartspaar.pricecatalogservice.repository;

import com.smartspaar.pricecatalogservice.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * JPA repo for prices.
 * It finds prices by query.
 */
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByQueryIgnoreCase(String query);
}