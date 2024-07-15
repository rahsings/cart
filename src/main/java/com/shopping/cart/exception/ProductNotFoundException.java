package com.shopping.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    public ProductNotFoundException(HttpStatus httpStatus, String message, String code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }
}
