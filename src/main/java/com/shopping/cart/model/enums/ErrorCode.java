package com.shopping.cart.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PRODUCT("PRODUCT NOT FOUND", "Invalid! Product not found for given PRODUCT_ID: %s"),
    USER("USER NOT FOUND", "Invalid! User not found for given USER_ID: %s"),
    PRODUCT_ID("PRODUCT ID NOT FOUND", "Product Id is empty,null or 0"),
    QUANTITY("QUANTITY NOT FOUND", "Quantity value is either empty,null or 0"),
    USER_ID("USER ID NOT FOUND", "userID is null,empty or 0"),
    ADDRESS("ADDRESSES NOT FOUND", "Address not found for given USER_ID: %s"),
    CART("CART NOT FOUND", "Invalid! Cart not found for give CART_ID: %s"),
    CART_ITEM("CART ITEM NOT FOUND", "Invalid! Cart item not found for give ITEM_ID: %s"),
    CART_VALUE_INSUFFICIENT("INSUFFICIENT CART VALUE", "Invalid! Cannot remove as current Item size is %s and item to remove is %s for ITEM_ID: %s"),
    CART_ID("CART ID NOT FOUND", "cartId is null,empty or 0"),
    ITEM_ID("ITEM ID NOT FOUND","itemId is null,empty or 0");


    private final String key;
    private final String message;

    public static ErrorCode getField(String value) {
        for (ErrorCode field : ErrorCode.values()) {
            if (field.name().equalsIgnoreCase(value)) {
                return field;
            }
        }
        return null;
    }
}
