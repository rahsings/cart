package com.shopping.cart.model.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    private String key;
    private String message;
    private String code;
}
