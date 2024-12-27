package com.masprog.ice_market_api.controllers;

import com.masprog.ice_market_api.exceptions.ResourceNotFoundException;
import com.masprog.ice_market_api.models.Cart;
import com.masprog.ice_market_api.payload.CartDTO;
import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.payload.UpdateProductQuantityRequest;
import com.masprog.ice_market_api.repositories.CartRepository;
import com.masprog.ice_market_api.services.CartService;
import com.masprog.ice_market_api.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carrinho de compra", description = "Endpoints para gerenciar carrinhos de compras" )
@RestController
@RequestMapping("/api")
public class CartController {


    private final CartRepository cartRepository;


    private final AuthUtil authUtil;


    private final CartService cartService;

    public CartController(CartRepository cartRepository, AuthUtil authUtil, CartService cartService) {
        this.cartRepository = cartRepository;
        this.authUtil = authUtil;
        this.cartService = cartService;
    }

    @Operation(summary = "Adicionar produto ao carinho", description = "Requisição feita pelo user logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Endereço registado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class)))
            })
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos carrinhos", description = "Requisição feita pelo user logado Admin",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado com sucesso todos carrinhos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class)))
            })
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts(){
       List<CartDTO> cartDTOS = cartService.getAllCarts();
       return new ResponseEntity<List<CartDTO>>(cartDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Listar Carrinhos do user", description = "Requisição feita pelo user logado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado com sucesso todos carrinhos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class)))
            })
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById(){
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null ){
            throw new ResourceNotFoundException("Cart", "cartId", emailId);
        }
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }


    @Operation(summary = "Deletar produto do carrinho", description = "Requisição feita pelo user logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto deletado com sucesso do carrinho ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
            })
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar a quantidade do carrinho", description = "Requisição feita pelo user logado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso do carrinho ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class)))
            })
    @PutMapping("/carts/products/{productId}")
    public ResponseEntity<CartDTO> updateProductQuantityInCart(@PathVariable Long productId,
                                                               @RequestBody UpdateProductQuantityRequest request){
        CartDTO updatedCart = cartService.updateProductQuantityInCart(productId, request.getQuantity());
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
}
