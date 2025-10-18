package com.smartspaar.pricecatalogservice.model;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Price entity.
 * It maps product price in a store.
 */
@Entity
@Table(name = "prices")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String store;
    private String query;
    private String name;
    private String brand;
    private String quantity;
    private String packaging;
    @Column(name = "regular_price")
    private Double regularPrice;
    @Column(name = "discount_price")
    private Double discountPrice;
    private String currency;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "source_type")
    private String sourceType;
    private Integer confidence;
    @Column(name = "last_updated")
    private Instant lastUpdated;

    /** Getters/Setters omitted for brevity */
    // --- getters/setters quick ---
    public Long getId() { return id; }
    public String getStore() { return store; }
    public void setStore(String store) { this.store = store; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public String getPackaging() { return packaging; }
    public void setPackaging(String packaging) { this.packaging = packaging; }
    public Double getRegularPrice() { return regularPrice; }
    public void setRegularPrice(Double regularPrice) { this.regularPrice = regularPrice; }
    public Double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(Double discountPrice) { this.discountPrice = discountPrice; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    public Integer getConfidence() { return confidence; }
    public void setConfidence(Integer confidence) { this.confidence = confidence; }
    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }
}