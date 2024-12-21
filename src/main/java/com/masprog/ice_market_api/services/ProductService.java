package com.masprog.ice_market_api.services;

import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.payload.ProductResponse;

public interface ProductService {

    ProductDTO addProduct(ProductDTO product);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductDTO updateProduct(ProductDTO productDTO, Long id);

}
