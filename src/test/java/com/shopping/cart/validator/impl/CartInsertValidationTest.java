package com.shopping.cart.validator.impl;

import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.validator.ValidationErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartInsertValidationTest {

    private CartInsertValidation cartInsertValidation;

    @BeforeEach
    public void setUp() {
        cartInsertValidation = new CartInsertValidation();
    }

    @Test
    public void validate_validCartRequest() {
        CartRequestInsert validRequest = new CartRequestInsert();
        validRequest.setProductId(12L);
        validRequest.setQuantity(5);
        validRequest.setUserId(14L);

        ValidationErrors errors = cartInsertValidation.validate(validRequest);

        assertTrue(errors.getErrorMessageForField().isEmpty(), "Expected no validation errors");
    }

    @Test
    public void validate_invalidCartRequest() {
        CartRequestInsert invalidRequest = new CartRequestInsert();
        invalidRequest.setProductId(null);
        invalidRequest.setQuantity(0);
        invalidRequest.setUserId(null);

        ValidationErrors errors = cartInsertValidation.validate(invalidRequest);

        assertFalse(errors.getErrorMessageForField().isEmpty(), "Expected validation errors");

        assertTrue(errors.getErrorMessageForField().containsKey("product"));
        assertTrue(errors.getErrorMessageForField().containsKey("quantity"));
        assertTrue(errors.getErrorMessageForField().containsKey("user"));
    }

    @Test
    public void validate_partialInvalidCartRequest() {
        CartRequestInsert partialInvalidRequest = new CartRequestInsert();
        partialInvalidRequest.setProductId(null);
        partialInvalidRequest.setQuantity(2);
        partialInvalidRequest.setUserId(null);

        ValidationErrors errors = cartInsertValidation.validate(partialInvalidRequest);

        assertFalse(errors.getErrorMessageForField().isEmpty(), "Expected validation errors");

        assertTrue(errors.getErrorMessageForField().containsKey("product"));
        assertFalse(errors.getErrorMessageForField().containsKey("quantity"));
        assertTrue(errors.getErrorMessageForField().containsKey("user"));
    }
}
