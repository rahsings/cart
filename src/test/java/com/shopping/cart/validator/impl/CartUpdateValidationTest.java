package com.shopping.cart.validator.impl;

import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.validator.ValidationErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartUpdateValidationTest {

    private CartUpdateValidation cartUpdateValidation;

    @BeforeEach
    public void setUp() {
        cartUpdateValidation = new CartUpdateValidation();
    }

    @Test
    public void validate_validCartRequest() {
        CartRequestUpdate validRequest = new CartRequestUpdate();
        validRequest.setItemId(12L);
        validRequest.setQuantity(5);
        validRequest.setCartId(14L);

        ValidationErrors errors = cartUpdateValidation.validate(validRequest);

        assertTrue(errors.getErrorMessageForField().isEmpty(), "Expected no validation errors");
    }

    @Test
    public void validate_invalidCartRequest() {
        CartRequestUpdate invalidRequest = new CartRequestUpdate();
        invalidRequest.setItemId(null);
        invalidRequest.setQuantity(0);
        invalidRequest.setCartId(null);

        ValidationErrors errors = cartUpdateValidation.validate(invalidRequest);

        assertFalse(errors.getErrorMessageForField().isEmpty(), "Expected validation errors");

        assertTrue(errors.getErrorMessageForField().containsKey("item"));
        assertTrue(errors.getErrorMessageForField().containsKey("cart"));
        assertTrue(errors.getErrorMessageForField().containsKey("quantity"));
    }

    @Test
    public void validate_partialInvalidCartRequest() {
        CartRequestUpdate partialInvalidRequest = new CartRequestUpdate();
        partialInvalidRequest.setCartId(null);
        partialInvalidRequest.setQuantity(2);
        partialInvalidRequest.setCartId(null);

        ValidationErrors errors = cartUpdateValidation.validate(partialInvalidRequest);

        assertFalse(errors.getErrorMessageForField().isEmpty(), "Expected validation errors");

        assertTrue(errors.getErrorMessageForField().containsKey("item"));
        assertTrue(errors.getErrorMessageForField().containsKey("cart"));
        assertFalse(errors.getErrorMessageForField().containsKey("quantity"));
    }
}
