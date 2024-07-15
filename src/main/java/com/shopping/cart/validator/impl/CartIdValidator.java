package com.shopping.cart.validator.impl;

import com.shopping.cart.validator.AbstractValidator;
import com.shopping.cart.validator.ValidationErrors;
import org.springframework.stereotype.Component;

@Component
public class CartIdValidator extends AbstractValidator<Long> {

    private static final String CART_ID = "CART_ID";

    @Override
    public ValidationErrors validate(Long cartId) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.add(validate(cartId, CART_ID));
        return validationErrors;
    }
}
