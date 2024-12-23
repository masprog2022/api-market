package com.masprog.ice_market_api.controllers;

import com.masprog.ice_market_api.config.AppConstants;
import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.payload.ProductResponse;
import com.masprog.ice_market_api.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produtos", description = "Endpoints para criar, actualizar, listar e deletar produtos" )
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Criar Produto", description = "Requisição feita somente pelos ADMIN do sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
            })
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        ProductDTO savedProductDTO = productService.addProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos Produtos", description = "Todos podem ver os produtos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto listado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)))
            })
    @GetMapping("/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
       ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar Produto", description = "Requisição feita somente pelos ADMIN do sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto actualizado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
            })
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
        ProductDTO savedProductDTO = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.OK);
    }

    @Operation(summary = "Remover Produto", description = "Requisição feita somente pelos ADMIN do sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto removido com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
            })
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deleteProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deleteProduct, HttpStatus.OK);
    }

}

