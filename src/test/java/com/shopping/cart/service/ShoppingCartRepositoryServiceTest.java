package com.shopping.cart.service;

import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.Product;
import com.shopping.cart.model.entity.ShoppingCart;
import com.shopping.cart.model.entity.User;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.repository.ShoppingCartRepository;
import com.shopping.cart.service.ext.ProductService;
import com.shopping.cart.service.ext.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartRepositoryServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ShoppingCartRepositoryService shoppingCartService;

    private final Long userId = 1L;
    private final Long cartId = 1L;
    private final Long productId = 2L;
    private ShoppingCart shoppingCart;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userService.get(userId));

        cartItem = new CartItem();
        cartItem.setId(1L);
        shoppingCart.setCartItems(Set.of(cartItem));
    }

    @Test
    void test_GetShoppingCart_CartExists() {
        when(shoppingCartRepository.findByUser(userId)).thenReturn(Optional.of(shoppingCart));

        ShoppingCart result = shoppingCartService.getShoppingCart(userId);

        assertEquals(shoppingCart, result);
        verify(shoppingCartRepository).findByUser(userId);
    }

    @Test
    void test_GetShoppingCart_CartDoesNotExist() {

        when(shoppingCartRepository.findByUser(userId)).thenReturn(Optional.empty());
        when(userService.get(userId)).thenReturn(new User());

        ShoppingCart result = shoppingCartService.getShoppingCart(userId);

        assertNotNull(result);
        assertNull(result.getUser());
        verify(shoppingCartRepository).findByUser(userId);
    }

    @Test
    void test_FindCartItem_ItemExists() {
        CartItem result = shoppingCartService.findCartItem(shoppingCart, cartItem.getId());
        assertEquals(cartItem, result);
    }

    @Test
    void test_FindCartItem_ItemDoesNotExist() {
        assertThrows(RuntimeException.class, () -> shoppingCartService.findCartItem(shoppingCart, 999L));
    }

    @Test
    void test_Get_CartExists() {
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

        ShoppingCart result = shoppingCartService.get(cartId);

        assertEquals(shoppingCart, result);
    }

    @Test
    void test_Get_CartDoesNotExist() {
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> shoppingCartService.get(cartId));
    }

    @Test
    void test_DeleteCartIfEmpty_CartIsEmpty() {
        ShoppingCart emptyCart = new ShoppingCart();
        emptyCart.setCartItems(Collections.emptySet());

        shoppingCartService.deleteCartIfEmpty(emptyCart);

        verify(shoppingCartRepository).delete(emptyCart);
    }

    @Test
    void test_DeleteCartIfEmpty_CartIsNotEmpty() {
        shoppingCartService.deleteCartIfEmpty(shoppingCart);

        verify(shoppingCartRepository, never()).delete(any());
    }

    @Test
    void test_Save() {
        shoppingCartService.save(shoppingCart);

        verify(shoppingCartRepository).save(shoppingCart);
    }

    @Test
    void test_GetProduct() {
        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setProductId(productId);

        Product product = new Product();
        when(productService.get(productId)).thenReturn(product);

        Product result = shoppingCartService.getProduct(cartRequestInsert);

        assertEquals(product, result);
    }

    @Test
    void test_EvictCartItemsCacheForCartId() {
        shoppingCartService.evictCartItemsCacheForCartId(cartId);
    }

    @Test
    void test_EvictCartItemsCacheForUserId() {
        shoppingCartService.evictCartItemsCacheForUserId(userId);
    }
}
