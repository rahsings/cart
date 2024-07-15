package com.shopping.cart.model.response;

import com.shopping.cart.model.validator.ErrorDetail;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final HttpStatus message;
    private final Map<String, List<ErrorDetail>> errors;

    public ErrorResponse(HttpStatus message, Map<String, List<ErrorDetail>> errors) {
        this.message = message;
        this.errors = errors;
    }
}
