package com.shopping.cart.service.ext.impl;

import com.shopping.cart.exception.ProductNotFoundException;
import com.shopping.cart.mapper.ProductMapper;
import com.shopping.cart.model.dto.ProductDTO;
import com.shopping.cart.model.entity.Product;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.repository.ProductRepository;
import com.shopping.cart.service.ext.ProductService;
import com.shopping.cart.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "productById", key = "#id")
    public Product get(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(HttpStatus.BAD_REQUEST,
                        Utils.get(id, ErrorCode.PRODUCT.getMessage()),ErrorCode.PRODUCT.getKey()));
    }


    @Cacheable(value = "products")
    public List<ProductDTO> getAll() {
        return productMapper.productToProductDTO(productRepository.findAll());
    }

    @CacheEvict(value = "products", allEntries = true)
    public void evictCacheProducts() {
    }

    @Transactional
    @CacheEvict(value = "productById", key = "#id")
    public void evictCacheForID(Long id) {
    }
}
