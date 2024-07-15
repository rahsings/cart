package com.shopping.cart.util;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.Product;
import com.shopping.cart.model.entity.ShoppingCart;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.model.response.CartResponse;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Set;

public class Utils {

    public static String get(Object key,String message){
        return String.format(message,key);
    }

    public static String get(Object key1,Object key2,Object key3,String message) {
        return String.format(message, key1, key2, key3);
    }

    public static CartItem createCartItem(ShoppingCart shoppingCart, Product product, int quantity) {
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setShoppingCart(shoppingCart);
        return newCartItem;
    }

    public static CartResponse createCartResponse(Long cartId, Set<CartItemDTO> cartItems, BigDecimal total) {
        CartResponse response = new CartResponse();
        response.setCartId(cartId);
        response.setCartItemSet(cartItems);
        response.setTotal(total);
        return response;
    }

    public static CartNotFoundException createCartNotFoundException(Long id) {
        ErrorCode errorCode = ErrorCode.CART;
        return new CartNotFoundException(HttpStatus.BAD_REQUEST,
                Utils.get(id, errorCode.getMessage()), errorCode.getKey());
    }

    public static CartNotFoundException createCartItemNotFoundException(Long id) {
        ErrorCode errorCode = ErrorCode.CART_ITEM;
        return new CartNotFoundException(HttpStatus.BAD_REQUEST,
                Utils.get(id, errorCode.getMessage()), errorCode.getKey());
    }
}
