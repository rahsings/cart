package com.shopping.cart.service.impl;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.mapper.CartMapper;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.dto.ShoppingCartDTO;
import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.Product;
import com.shopping.cart.model.entity.ShoppingCart;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.service.CartService;
import com.shopping.cart.service.ShoppingCartRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartRepositoryService shoppingCartRepositoryService;

    @Mock
    private CartService cartService;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    private ShoppingCart shoppingCart;
    private Product product;
    private CartRequestInsert cartRequestInsert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(new HashSet<>());

        product = new Product();
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(10.00));

        cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(1L);
        cartRequestInsert.setProductId(1L);
        cartRequestInsert.setQuantity(2);
    }

    @Test
    void test_addItemToCart_shouldAddItemSuccessfully() {
        when(shoppingCartRepositoryService.getShoppingCart(cartRequestInsert.getUserId())).thenReturn(shoppingCart);
        when(shoppingCartRepositoryService.getProduct(cartRequestInsert)).thenReturn(product);

        CartItem mockCartItem = new CartItem();
        mockCartItem.setProduct(product);
        mockCartItem.setQuantity(2);
        shoppingCart.getCartItems().add(mockCartItem);

        doNothing().when(cartService).add(any(), any());
        when(shoppingCartMapper.shoppingToShoppingDTO(any())).thenReturn(new ShoppingCartDTO());

        ShoppingCartDTO result = shoppingCartService.addItemToCart(cartRequestInsert);

        assertNotNull(result);
        verify(shoppingCartRepositoryService).save(shoppingCart);
    }

    @Test
    void test_updateItemQuantity_shouldUpdateQuantitySuccessfully() {
        Long cartId = 1L;
        Long itemId = 1L;
        int quantity = 2;

        when(shoppingCartRepositoryService.get(cartId)).thenReturn(shoppingCart);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        shoppingCart.getCartItems().add(cartItem);

        when(shoppingCartRepositoryService.findCartItem(shoppingCart, itemId)).thenReturn(cartItem);

        when(shoppingCartMapper.shoppingToShoppingDTO(any())).thenReturn(new ShoppingCartDTO());

        CartRequestUpdate cartRequestInsert1 = new CartRequestUpdate();
        cartRequestInsert1.setItemId(itemId);
        cartRequestInsert1.setQuantity(quantity);
        cartRequestInsert1.setCartId(cartId);

        ShoppingCartDTO result = shoppingCartService.updateItemQuantity(cartRequestInsert1);

        assertNotNull(result);
        assertEquals(2, cartItem.getQuantity());
        verify(shoppingCartRepositoryService).deleteCartIfEmpty(shoppingCart);
    }

    @Test
    void test_removeItemFromCart_shouldRemoveItemSuccessfully() {
        Long cartId = 1L;
        Long itemId = 1L;

        when(shoppingCartRepositoryService.get(cartId)).thenReturn(shoppingCart);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        shoppingCart.getCartItems().add(cartItem);

        when(shoppingCartRepositoryService.findCartItem(shoppingCart, itemId)).thenReturn(cartItem);

        when(shoppingCartMapper.shoppingToShoppingDTO(any())).thenReturn(new ShoppingCartDTO());

        doNothing().when(cartService).remove(any(),any());
        doNothing().when(shoppingCartRepositoryService).deleteCartIfEmpty(shoppingCart);

        CartRequestDelete cartRequestInsert1 = new CartRequestDelete();
        cartRequestInsert1.setCartId(cartId);
        cartRequestInsert1.setItemId(itemId);
        ShoppingCartDTO result = shoppingCartService.removeItemFromCart(cartRequestInsert1);

        assertNotNull(result);
        assertTrue(result.getCartItems().isEmpty());
        verify(shoppingCartRepositoryService).deleteCartIfEmpty(shoppingCart);
    }

    @Test
    void test_getCartTotal_shouldCalculateTotalSuccessfully() {
        Long cartId = 1L;
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        shoppingCart.getCartItems().add(cartItem);

        when(cartService.findByShoppingCart(cartId)).thenReturn(new HashSet<>(Collections.singletonList(cartItem)));
        when(cartService.getCartItem(anySet())).thenReturn(new HashSet<>());

        CartResponse expectedResponse = new CartResponse();
        expectedResponse.setTotal(BigDecimal.valueOf(20.00));

        when(shoppingCartMapper.shoppingToShoppingDTO(any())).thenReturn(new ShoppingCartDTO());
        CartResponse result = shoppingCartService.getCartTotal(cartId);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(20.00), result.getTotal());
    }

    @Test
    void test_getCart_shouldReturnCartSuccessfully() {
        Long cartId = 1L;
        when(shoppingCartRepositoryService.get(cartId)).thenReturn(shoppingCart);
        when(shoppingCartMapper.shoppingToShoppingDTO(any())).thenReturn(new ShoppingCartDTO());

        ShoppingCartDTO result = shoppingCartService.getCart(cartId);

        assertNotNull(result);
    }

    @Test
    void test_getCartTotal_shouldThrowCartNotFoundException_whenCartIsEmpty() {
        Long cartId = 1L;

        when(cartService.findByShoppingCart(cartId)).thenReturn(Collections.emptySet());

        assertThrows(CartNotFoundException.class, () -> shoppingCartService.getCartTotal(cartId));
    }
}
