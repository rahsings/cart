package com.shopping.cart.service;

import com.shopping.cart.exception.InvalidInputException;

public interface ValidationService {
    <T> void validateObject(Class<T> type, T object) throws InvalidInputException;
}
