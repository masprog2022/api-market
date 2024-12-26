package com.masprog.ice_market_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_order_items")
public class OrdemItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal orderedProductPrice;

    public OrdemItem() {
    }

    public OrdemItem(Long orderItemId, Product product, Integer quantity, BigDecimal orderedProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.orderedProductPrice = orderedProductPrice;
    }


    public BigDecimal calculateSubTotal() {
        return orderedProductPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public @NotNull @Min(1) Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull @Min(1) Integer quantity) {
        this.quantity = quantity;
    }

    public @NotNull @DecimalMin("0.00") BigDecimal getOrderedProductPrice() {
        return orderedProductPrice;
    }

    public void setOrderedProductPrice(@NotNull @DecimalMin("0.00") BigDecimal orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }
}


