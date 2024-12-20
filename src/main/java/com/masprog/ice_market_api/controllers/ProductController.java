package com.masprog.ice_market_api.controllers;

import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        ProductDTO savedProductDTO = productService.addProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }
}

