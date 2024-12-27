package com.masprog.ice_market_api.services.impl;

import com.masprog.ice_market_api.exceptions.APIException;
import com.masprog.ice_market_api.exceptions.ResourceNotFoundException;
import com.masprog.ice_market_api.models.Cart;
import com.masprog.ice_market_api.models.CartItem;
import com.masprog.ice_market_api.models.Product;
import com.masprog.ice_market_api.payload.CartDTO;
import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.repositories.CartItemRepository;
import com.masprog.ice_market_api.repositories.CartRepository;
import com.masprog.ice_market_api.repositories.ProductRepository;
import com.masprog.ice_market_api.services.CartService;
import com.masprog.ice_market_api.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;

    private final AuthUtil authUtil;

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    @Autowired
    ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, AuthUtil authUtil, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.authUtil = authUtil;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {

        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

        if(cartItem != null){
            throw new APIException("Product " + product.getProductName() + "already exists in the cart");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setProductPrice(product.getPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));

        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO>  productStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if (carts.isEmpty()){
            throw new APIException("No cart exists");
        }

        return carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream().map(cartItem -> {
                ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                productDTO.setQuantity(cartItem.getQuantity()); // Set the quantity from CartItem
                return productDTO;
            }).collect(Collectors.toList());


            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
       Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);
       if(cart == null){
           throw new ResourceNotFoundException("Cart", "cartId", cartId);
       }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(c ->
                c.getProduct().setQuantity(c.getQuantity()));
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
        String emailId = authUtil.loggedInEmail();

        // Validação e busca do carrinho do usuário
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "emailId", emailId);
        }

        // Busca do produto
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        // Busca do item do carrinho
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);
        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        // Cálculo da nova quantidade
        int newQuantity = cartItem.getQuantity() + quantity;
        if (newQuantity < 0) {
            throw new APIException("The resulting quantity cannot be negative.");
        }

        // Atualização ou remoção do item
        if (newQuantity == 0) {
            deleteProductFromCart(cart.getCartId(), productId);
        } else {
            cartItem.setProductPrice(product.getPrice());
            cartItem.setQuantity(newQuantity);
        }

        // Recalcular o total do carrinho
        double newTotalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProductPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(newTotalPrice);
        cartRepository.save(cart);

        // Conversão para DTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(item -> {
                    ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
                    prd.setQuantity(item.getQuantity());
                    return prd;
                })
                .toList();
        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }


    private Cart createCart() {
        Cart userCart  = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null){
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());

        return cartRepository.save(cart);
    }



  /*  @Override
    public void updateProductInCarts(Long cartId, Long productId) {
       Cart cart = cartRepository.findById(cartId)
               .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice()
                - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getPrice());

        cart.setTotalPrice(cartPrice
                + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItem = cartItemRepository.save(cartItem);
    }*/
}
