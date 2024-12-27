package com.masprog.ice_market_api.payload;


import lombok.*;

import java.math.BigDecimal;

public class ProductDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private Integer quantity;
    private double price;
    private boolean availability;

    public ProductDTO(){}

    public ProductDTO(String productName, String productDescription, Integer quantity, double price, boolean availability) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
