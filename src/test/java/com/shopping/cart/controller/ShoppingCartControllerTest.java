package com.shopping.cart.controller;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.exception.ProductNotFoundException;
import com.shopping.cart.exception.UserNotFoundException;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.model.response.CartResponseTotal;
import com.shopping.cart.service.ShoppingCartService;
import com.shopping.cart.service.ValidationService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mockAddItemToCart() {
        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        CartResponse cartResponse = new CartResponse();

        when(shoppingCartService.addItemToCart(any(CartRequestInsert.class))).thenReturn(cartResponse);

        ResponseEntity<CartResponse> response = shoppingCartController.addItemToCart(cartRequestInsert);
        assertEquals(cartResponse, response.getBody());
    }

    @Test
    void mockUpdateItemQuantity() {
        Long cartId = 1L;
        CartRequestUpdate cartRequestInsert = new CartRequestUpdate();
        cartRequestInsert.setItemId(2L);
        cartRequestInsert.setQuantity(3);
        CartResponse cartResponse = new CartResponse();

        when(shoppingCartService.updateItemQuantity(any())).thenReturn(cartResponse);

        ResponseEntity<CartResponse> response = shoppingCartController.updateItemQuantity(cartId, cartRequestInsert);
        assertEquals(cartResponse, response.getBody());
    }

    @Test
    void mockRemoveItemFromCart() {
        Long cartId = 1L;
        CartRequestDelete cartRequestInsert = new CartRequestDelete();
        cartRequestInsert.setItemId(2L);
        CartResponse cartResponse = new CartResponse();

        when(shoppingCartService.removeItemFromCart(any())).thenReturn(cartResponse);

        ResponseEntity<CartResponse> response = shoppingCartController.removeItemFromCart(cartId, cartRequestInsert);
        assertEquals(cartResponse, response.getBody());
    }

    @Test
    void mockGetCart() throws CartNotFoundException {
        Long cartId = 1L;
        CartResponse cartResponse = new CartResponse();

        when(shoppingCartService.getCart(anyLong())).thenReturn(cartResponse);

        ResponseEntity<CartResponse> response = shoppingCartController.getCart(cartId);
        assertEquals(cartResponse, response.getBody());
    }

    @Test
    void mockGetCartTotal() {
        Long cartId = 1L;
        CartResponseTotal cartResponseTotal = new CartResponseTotal();
        cartResponseTotal.setTotal(BigDecimal.valueOf(100.00));

        when(shoppingCartService.getCartTotal(anyLong())).thenReturn(cartResponseTotal);

        ResponseEntity<CartResponseTotal> response = shoppingCartController.getCartTotal(cartId);
        assertEquals(cartResponseTotal, response.getBody());
    }

    @Test
    void mockAddItemToCartValidationException() {
        CartRequestInsert cartRequestInsert = new CartRequestInsert();

        doThrow(new ValidationException("Validation error")).when(validationService).validateObject(CartRequestInsert.class, cartRequestInsert);

        ValidationException exception = assertThrows(ValidationException.class, () -> shoppingCartController.addItemToCart(cartRequestInsert));

        assertEquals("Validation error", exception.getMessage());
    }

    @Test
    void mockAddItemToCartProductNotFoundException() {
        CartRequestInsert cartRequestInsert = new CartRequestInsert();

        when(shoppingCartService.addItemToCart(any(CartRequestInsert.class))).thenThrow(new ProductNotFoundException(HttpStatus.BAD_REQUEST, ErrorCode.PRODUCT.getMessage(), ErrorCode.PRODUCT.getKey()));

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> shoppingCartController.addItemToCart(cartRequestInsert));
        assertEquals("PRODUCT NOT FOUND", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void mockAddItemToCartUserNotFoundException() {
        CartRequestInsert cartRequestInsert = new CartRequestInsert();

        when(shoppingCartService.addItemToCart(any(CartRequestInsert.class))).thenThrow(new UserNotFoundException(HttpStatus.BAD_REQUEST, ErrorCode.USER.getMessage(), ErrorCode.USER.getKey()));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> shoppingCartController.addItemToCart(cartRequestInsert));

        assertEquals("USER NOT FOUND", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}

