package com.shopping.cart.service.ext.impl;

import com.shopping.cart.exception.ProductNotFoundException;
import com.shopping.cart.mapper.ProductMapper;
import com.shopping.cart.model.dto.ProductDTO;
import com.shopping.cart.model.entity.Product;
import com.shopping.cart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = createProduct();
        productDTO = createProductDTO();
    }

    @Test
    void test_GetProductFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.get(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        verify(productRepository).findById(1L);
    }

    @Test
    void test_GetProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productService.get(1L));

        assertEquals("Invalid! Product not found for given PRODUCT_ID: 1", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,  exception.getHttpStatus());
    }

    @Test
    void test_GetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productMapper.productToProductDTO(Collections.singletonList(product)))
                .thenReturn(Collections.singletonList(productDTO));

        List<ProductDTO> result = productService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO.getId(), result.get(0).getId());
        verify(productRepository).findAll();
    }

    @Test
    void test_EvictCacheProducts() {
        productService.evictCacheProducts();
        verify(productRepository, never()).findAll();
    }

    @Test
    void test_EvictCacheForID() {
        productService.evictCacheForID(1L);
        verify(productRepository, never()).findById(anyLong());
    }

    private ProductDTO createProductDTO() {
        ProductDTO p1 = new ProductDTO();
        p1.setId(1L);
        p1.setPrice(new BigDecimal("399.00"));
        p1.setBrand("Nike");
        p1.setName("Running Shoes");
        p1.setProdType("Comfortable Running Shoes");
        p1.setProdInfo("Nice shoes for runner");
        return p1;
    }

    private Product createProduct(){
        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal("699.00"));
        p2.setBrand("Apple");
        p2.setName("Iphone 13");
        p2.setProdType("Electronics");
        p2.setProdInfo("Latest smartphone with cutting-edge featuresr");

        return p2;
    }
}

