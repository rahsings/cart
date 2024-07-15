package com.shopping.cart.validator.impl;

import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.validator.AbstractValidator;
import com.shopping.cart.validator.ValidationErrors;
import org.springframework.stereotype.Component;

@Component
public class CartDeleteValidation extends AbstractValidator<CartRequestDelete> {

    private static final String ITEM_ID = "ITEM_ID";
    private static final String CART_ID = "CART_ID";

    @Override
    public ValidationErrors validate(CartRequestDelete cartRequestDelete) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.add(validate(cartRequestDelete.getItemId(), ITEM_ID));
        validationErrors.add(validate(cartRequestDelete.getCartId(), CART_ID));
        return validationErrors;
    }
}
