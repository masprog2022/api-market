package com.masprog.ice_market_api.services.impl;

import com.masprog.ice_market_api.exceptions.APIException;
import com.masprog.ice_market_api.models.Product;
import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.repositories.ProductRepository;
import com.masprog.ice_market_api.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ModelMapper modelMapper;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {

        this.productRepository = productRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product productFromDb = productRepository.findByProductName(product.getProductName());
        if (productFromDb != null){
            throw new APIException("Produto com o nome " + product.getProductName() +" Já existe");
        }
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);

    }
}
