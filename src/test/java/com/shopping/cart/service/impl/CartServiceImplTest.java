package com.shopping.cart.service.impl;

import com.shopping.cart.exception.CartValueInSufficientException;
import com.shopping.cart.mapper.CartMapper;
import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.ShoppingCart;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartMapper cartMapper;

    private ShoppingCart shoppingCart;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCart = new ShoppingCart();
        cartItem = new CartItem();
    }

    @Test
    void test_Add() {
        cartService.add(cartItem, shoppingCart);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void test_Remove() {
        cartService.remove(cartItem, shoppingCart);
        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void test_AddOrRemoveQuantityToCart_Add() {
        int quantityToAdd = 5;
        cartItem.setQuantity(0);

        cartService.addOrRemoveQuantityToCart(cartItem, shoppingCart, quantityToAdd);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void test_AddOrRemoveQuantityToCart_Remove() {
        int quantityToRemove = -2;
        cartItem.setQuantity(3);

        cartService.addOrRemoveQuantityToCart(cartItem, shoppingCart, quantityToRemove);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void test_RemoveItemFromCartByQuantity_InsufficientQuantity() {
        cartItem.setQuantity(1);
        int quantityToRemove = 2;

        CartValueInSufficientException exception = assertThrows(CartValueInSufficientException.class, () ->
                cartService.removeItemFromCartByQuantity(shoppingCart, cartItem, quantityToRemove)
        );

        String expected = String.format(ErrorCode.CART_VALUE_INSUFFICIENT.getMessage(), 1, 2, null);

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void test_FindByShoppingCart() {
        List<CartItem> cartItems = new ArrayList<>();
        when(cartItemRepository.findByShoppingCart_Id(1L)).thenReturn(cartItems);

        Set<CartItem> result = cartService.findByShoppingCart(1L);

        assertEquals(new HashSet<>(cartItems), result);
        verify(cartItemRepository).findByShoppingCart_Id(1L);
    }

    @Test
    void test_GetCartItem() {
        Set<CartItem> cartItems = new HashSet<>();
        when(cartMapper.cartToCartItemDTO(cartItems)).thenReturn(new HashSet<>());

        Set<CartItemDTO> result = cartService.getCartItem(cartItems);

        assertNotNull(result);
        verify(cartMapper).cartToCartItemDTO(cartItems);
    }
}

