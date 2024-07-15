package com.shopping.cart.service.ext;

import com.shopping.cart.model.dto.ProductDTO;
import com.shopping.cart.model.entity.Product;

import java.util.List;

public interface ProductService {
    Product get(Long id);

    List<ProductDTO> getAll();
}
