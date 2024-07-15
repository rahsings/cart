package com.shopping.cart.service.impl;

import com.shopping.cart.exception.CartValueInSufficientException;
import com.shopping.cart.mapper.CartMapper;
import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.ShoppingCart;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.repository.CartItemRepository;
import com.shopping.cart.service.CartService;
import com.shopping.cart.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Transactional
    @CacheEvict(value = "cartItemsByCartId", key = "#cart.id")
    public void remove(CartItem cartItem, ShoppingCart cart) {
        cart.removeCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    @CacheEvict(value = "cartItemsByCartId", key = "#cart.id")
    public void add(CartItem cartItem, ShoppingCart cart) {
        cart.addCartItem(cartItem);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    @CacheEvict(value = "cartItemsByCartId", key = "#cart.id")
    public void addOrRemoveQuantityToCart(CartItem cartItem, ShoppingCart cart, int quantity) {
        if (quantity > 0) {
            cartItem = addItemToCartByQuantity(cart, cartItem, quantity);
        } else if (-1 * quantity == cartItem.getQuantity()) {
            remove(cartItem, cart);
            return;
        } else {
            cartItem = removeItemFromCartByQuantity(cart, cartItem, -1 * quantity);
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public Set<CartItemDTO> getCartItem(Set<CartItem> cartItems) {
        return cartMapper.cartToCartItemDTO(cartItems);
    }

    @Transactional
    public CartItem removeItemFromCartByQuantity(ShoppingCart cart, CartItem cartItem, int quantity) {
        if (cartItem.getQuantity() < quantity) {
            ErrorCode errorCode = ErrorCode.CART_VALUE_INSUFFICIENT;
            throw new CartValueInSufficientException(HttpStatus.BAD_REQUEST,
                    Utils.get(cartItem.getQuantity(), quantity, cartItem.getId(), errorCode.getMessage()), errorCode.getKey());
        }
        return cart.removeCartItemByQuantity(cartItem, quantity);
    }

    @Transactional
    public CartItem addItemToCartByQuantity(ShoppingCart cart, CartItem cartItem, int quantity) {
        return cart.addCartItemByQuantity(cartItem, quantity);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cartItemsByCartId", key = "#id")
    public Set<CartItem> findByShoppingCart(Long id) {
        return new HashSet<>(cartItemRepository.findByShoppingCart_Id(id));
    }
}
