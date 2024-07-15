package com.shopping.cart.validator.impl;

import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.validator.AbstractValidator;
import com.shopping.cart.validator.ValidationErrors;
import org.springframework.stereotype.Component;

@Component
public class CartInsertValidation extends AbstractValidator<CartRequestInsert> {

    private static final String PRODUCT_ID = "PRODUCT_ID";
    private static final String QUANTITY = "QUANTITY";
    private static final String USER_ID = "USER_ID";

    @Override
    public ValidationErrors validate(CartRequestInsert cartRequestInsert) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.add(validate(cartRequestInsert.getUserId(), USER_ID));
        validationErrors.add(validate(cartRequestInsert.getProductId(), PRODUCT_ID));
        validationErrors.add(validate(cartRequestInsert.getQuantity(), QUANTITY));
        return validationErrors;
    }

}
