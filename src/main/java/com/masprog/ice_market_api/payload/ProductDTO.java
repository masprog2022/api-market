package com.masprog.ice_market_api.payload;


import lombok.*;

import java.math.BigDecimal;

public class ProductDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private boolean availability;

    public ProductDTO(){}

    public ProductDTO(Long productId, String productName, String productDescription, BigDecimal price, boolean availability) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.availability = availability;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
