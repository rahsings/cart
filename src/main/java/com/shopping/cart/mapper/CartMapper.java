package com.shopping.cart.mapper;

import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem cartDTOToCartItem(CartItemDTO cartItem);


    Set<CartItemDTO> cartToCartItemDTO(Set<CartItem> cartItem);
}
