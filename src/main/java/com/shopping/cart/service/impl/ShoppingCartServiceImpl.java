package com.shopping.cart.service.impl;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.dto.ShoppingCartDTO;
import com.shopping.cart.model.entity.*;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.service.CartService;
import com.shopping.cart.service.ShoppingCartRepositoryService;
import com.shopping.cart.service.ShoppingCartService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

import static com.shopping.cart.util.Utils.*;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepositoryService shoppingCartRepositoryService;
    private final CartService cartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @Transactional
    @CacheEvict(value = "shoppingCartByUserId", key = "#item.userId")
    public ShoppingCartDTO addItemToCart(CartRequestInsert item) {
        ShoppingCart shoppingCart = shoppingCartRepositoryService.getShoppingCart(item.getUserId());
        Product product = shoppingCartRepositoryService.getProduct(item);

        CartItem cartItem = findOrCreateCartItem(shoppingCart, product, item.getQuantity());
        shoppingCartRepositoryService.save(shoppingCart);
        cartService.add(cartItem, shoppingCart);
        return shoppingCartMapper.shoppingToShoppingDTO(shoppingCart);
    }

    private CartItem findOrCreateCartItem(ShoppingCart shoppingCart, Product product, int quantity) {
        return shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findAny()
                .map(cartItem -> {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    return cartItem;
                })
                .orElseGet(() -> createCartItem(shoppingCart, product, quantity));
    }

    @Transactional
    @CacheEvict(value = "cartItemsByCartId", key = "#cartRequestInsert.cartId")
    public ShoppingCartDTO updateItemQuantity(CartRequestUpdate cartRequestUpdate) {
        ShoppingCart cart = shoppingCartRepositoryService.get(cartRequestUpdate.getCartId());
        CartItem cartItem = shoppingCartRepositoryService.findCartItem(cart, cartRequestUpdate.getItemId());
        cartService.addOrRemoveQuantityToCart(cartItem, cart, cartRequestUpdate.getQuantity());
        shoppingCartRepositoryService.deleteCartIfEmpty(cart);
        return shoppingCartMapper.shoppingToShoppingDTO(cart);
    }

    @Transactional
    @CacheEvict(value = "cartItemsByCartId", key = "#cartRequestInsert.cartId")
    public ShoppingCartDTO removeItemFromCart(CartRequestDelete cartRequestDelete) {
        ShoppingCart cart = shoppingCartRepositoryService.get(cartRequestDelete.getCartId());
        CartItem cartItem = shoppingCartRepositoryService.findCartItem(cart, cartRequestDelete.getItemId());

        cartService.remove(cartItem, cart);
        shoppingCartRepositoryService.deleteCartIfEmpty(cart);
        return shoppingCartMapper.shoppingToShoppingDTO(cart);
    }

    @Transactional(readOnly = true)
    public CartResponse getCartTotal(Long cartId) {
        Set<CartItem> cartItems = cartService.findByShoppingCart(cartId);

        if (cartItems.isEmpty()) {
            throw createCartNotFoundException(cartId);
        }
        BigDecimal total = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return createCartResponse(cartId, cartService.getCartItem(cartItems), total);
    }

    public ShoppingCartDTO getCart(Long cartId) throws CartNotFoundException {
        ShoppingCart shoppingCart = shoppingCartRepositoryService.get(cartId);
        return shoppingCartMapper.shoppingToShoppingDTO(shoppingCart);
    }
}

