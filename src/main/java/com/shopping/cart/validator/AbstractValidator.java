package com.shopping.cart.validator;

import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.model.enums.ErrorField;
import com.shopping.cart.model.validator.ValidationError;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

public abstract class AbstractValidator<T> implements Validator<T> {

    public ValidationError validate(Object value, String key) {
        if (!isNullOrEmpty(value)) return null;
        ErrorCode errorCode = Objects.requireNonNull(ErrorCode.getField(key));
        return new ValidationError(Objects.requireNonNull(ErrorField.getField(key)).getField(),
                errorCode.getKey(), errorCode.getMessage());
    }

    private boolean isNullOrEmpty(Object value) {
        if (ObjectUtils.isEmpty(value))
            return true;
        if (value instanceof Number)
            return ((Number) value).longValue() == 0;
        return false;
    }
}
