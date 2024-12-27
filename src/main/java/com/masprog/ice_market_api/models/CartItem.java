package com.masprog.ice_market_api.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Min(1)
    private Integer quantity;

    private double productPrice;

    public CartItem() {
    }

    public CartItem(Cart cart, Product product, Integer quantity, double productPrice) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(@NotNull @Min(1) Integer quantity) {
        this.quantity = quantity;
    }



}
