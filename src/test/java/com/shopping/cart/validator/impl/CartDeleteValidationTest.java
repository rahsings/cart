package com.shopping.cart.validator.impl;

import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.util.JsonUtils;
import com.shopping.cart.validator.ValidationErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartDeleteValidationTest {

    private CartDeleteValidation cartInsertValidation;

    @BeforeEach
    public void setUp() {
        cartInsertValidation = new CartDeleteValidation();
    }

    @Test
    public void validate_validCartRequest() {
        CartRequestDelete validRequest = new CartRequestDelete();
        validRequest.setItemId(12L);
        validRequest.setCartId(14L);

        ValidationErrors errors = cartInsertValidation.validate(validRequest);

        assertTrue(errors.getErrorMessageForField().isEmpty(), "Expected no validation errors");
    }

    @Test
    public void validate_invalidCartRequest() {
        CartRequestDelete invalidRequest = new CartRequestDelete();
        invalidRequest.setItemId(null);
        invalidRequest.setCartId(null);

        ValidationErrors errors = cartInsertValidation.validate(invalidRequest);

        assertFalse(errors.getErrorMessageForField().isEmpty(), "Expected validation errors");

        assertTrue(errors.getErrorMessageForField().containsKey("item"));
        assertTrue(errors.getErrorMessageForField().containsKey("cart"));
    }

    @Test
    public void validate_partialInvalidCartRequest() {
        CartRequestDelete partialInvalidRequest = new CartRequestDelete();
        partialInvalidRequest.setItemId(1L);
        partialInvalidRequest.setCartId(null);

        ValidationErrors errors = cartInsertValidation.validate(partialInvalidRequest);

        assertFalse(errors.getErrorMessageForField().isEmpty(), "Expected validation errors");

        assertTrue(errors.getErrorMessageForField().containsKey("cart"));
        assertFalse(errors.getErrorMessageForField().containsKey("item"));
    }
}
