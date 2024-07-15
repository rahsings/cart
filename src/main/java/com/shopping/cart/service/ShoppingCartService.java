package com.shopping.cart.service;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.model.dto.ShoppingCartDTO;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.model.response.CartResponse;

public interface ShoppingCartService {

    ShoppingCartDTO addItemToCart(CartRequestInsert item);

    ShoppingCartDTO updateItemQuantity(CartRequestUpdate item);

    ShoppingCartDTO removeItemFromCart(CartRequestDelete item);

    CartResponse getCartTotal(Long cartId);

    ShoppingCartDTO getCart(Long cartId) throws CartNotFoundException;
}
