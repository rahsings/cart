package com.shopping.cart.validator.impl;

import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.util.JsonUtils;
import com.shopping.cart.validator.AbstractValidator;
import com.shopping.cart.validator.ValidationErrors;
import org.springframework.stereotype.Component;

@Component
public class CartUpdateValidation extends AbstractValidator<CartRequestUpdate> {

    private static final String ITEM_ID = "ITEM_ID";
    private static final String CART_ID = "CART_ID";
    private static final String QUANTITY = "QUANTITY";

    @Override
    public ValidationErrors validate(CartRequestUpdate cartRequestUpdate) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.add(validate(cartRequestUpdate.getItemId(), ITEM_ID));
        validationErrors.add(validate(cartRequestUpdate.getCartId(), CART_ID));
        validationErrors.add(validate(cartRequestUpdate.getQuantity(), QUANTITY));
        return validationErrors;
    }
}
