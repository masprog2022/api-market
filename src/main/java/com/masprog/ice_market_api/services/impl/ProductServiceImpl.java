package com.masprog.ice_market_api.services.impl;

import com.masprog.ice_market_api.exceptions.APIException;
import com.masprog.ice_market_api.models.Product;
import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.payload.ProductResponse;
import com.masprog.ice_market_api.repositories.ProductRepository;
import com.masprog.ice_market_api.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()){
            throw new APIException("Nenhum produto encontrado");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;


    }


}
