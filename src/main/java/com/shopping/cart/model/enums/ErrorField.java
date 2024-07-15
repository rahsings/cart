package com.shopping.cart.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorField {

    PRODUCT_ID("PRODUCT"),
    USER_ID("USER"),
    QUANTITY("QUANTITY"),
    CART_ID("CART"),
    ITEM_ID("ITEM");

    private final String field;

    public static ErrorField getField(String value) {
        for (ErrorField field : ErrorField.values()) {
            if (field.name().equalsIgnoreCase(value)) {
                return field;
            }
        }
        return null;
    }
}
