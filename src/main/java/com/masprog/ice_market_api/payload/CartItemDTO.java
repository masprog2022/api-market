package com.masprog.ice_market_api.payload;

public class CartItemDTO {

    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO productDTO;
    private Integer quantity;
    private Double productPrice;

    public CartItemDTO() {
    }

    public CartItemDTO(Long cartItemId, CartDTO cart, ProductDTO productDTO, Integer quantity, Double productPrice) {
        this.cartItemId = cartItemId;
        this.cart = cart;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
