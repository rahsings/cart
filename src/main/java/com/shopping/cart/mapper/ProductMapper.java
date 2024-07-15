package com.shopping.cart.mapper;

import com.shopping.cart.model.dto.ProductDTO;
import com.shopping.cart.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    List<ProductDTO> productToProductDTO(List<Product> product);
    Product productDTOToProduct(ProductDTO productDTO);
}
