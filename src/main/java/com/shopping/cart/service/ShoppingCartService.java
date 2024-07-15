package com.shopping.cart.service;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.model.response.CartResponseTotal;

public interface ShoppingCartService {

    CartResponse addItemToCart(CartRequestInsert item);

    CartResponse updateItemQuantity(CartRequestUpdate item);

    CartResponse removeItemFromCart(CartRequestDelete item);

    CartResponseTotal getCartTotal(Long cartId);

    CartResponse getCart(Long cartId) throws CartNotFoundException;
}
