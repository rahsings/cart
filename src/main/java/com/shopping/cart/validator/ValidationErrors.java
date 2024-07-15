package com.shopping.cart.validator;

import com.shopping.cart.model.validator.ErrorDetail;
import com.shopping.cart.model.validator.ValidationError;

import java.util.*;

public class ValidationErrors extends ArrayList<ValidationError> {

    public Map<String, List<ErrorDetail>> getErrorMessageForField() {
        Map<String, List<ErrorDetail>> errorMap = new HashMap<>();
        this.stream()
                .filter(Objects::nonNull)
                .forEach(result -> errorMap.put(result.getKey().toLowerCase(), Collections.singletonList(new ErrorDetail(result.getCode(), result.getMessage()))));
        return errorMap;
    }
}
