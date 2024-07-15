package com.shopping.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    public UserNotFoundException(HttpStatus status, String message, String code) {
        this.httpStatus = status;
        this.message = message;
        this.code = code;
    }

}
