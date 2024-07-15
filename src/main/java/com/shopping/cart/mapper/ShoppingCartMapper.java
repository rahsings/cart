package com.shopping.cart.mapper;

import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    ShoppingCartMapper shoppingCartMapper = Mappers.getMapper(ShoppingCartMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);

    @Mapping(target = "cartItems", source = "cartItems")
    @Mapping(target = "user.addresses", source = "user.addresses")
    ShoppingCart shoppingDTOToShopping(CartResponse cartResponse);

    @Mapping(target = "cartItems", source = "cartItems")
    CartResponse shoppingToShoppingDTO(ShoppingCart shoppingCart);
}
