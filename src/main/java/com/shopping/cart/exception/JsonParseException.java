package com.shopping.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JsonParseException extends RuntimeException {

    private final String message;
    private final HttpStatus status;
    private final String code;

    public JsonParseException(String message,String code) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.code = code;
    }
}
