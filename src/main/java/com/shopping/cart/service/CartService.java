package com.shopping.cart.service;

import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.ShoppingCart;

import java.util.Set;

public interface CartService {

    void remove(CartItem cartItem, ShoppingCart cart);

    void add(CartItem cartItem, ShoppingCart cart);

    void addOrRemoveQuantityToCart(CartItem cartItem, ShoppingCart cart, int quantity);

    Set<CartItem> findByShoppingCart(Long id);

    Set<CartItemDTO> getCartItem(Set<CartItem> cartItem);
}
