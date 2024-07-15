package com.shopping.cart.validator;

public interface Validator<T> {
    ValidationErrors validate(T object);
}

