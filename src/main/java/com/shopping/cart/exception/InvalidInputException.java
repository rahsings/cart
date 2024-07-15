package com.shopping.cart.exception;

import com.shopping.cart.model.validator.ErrorDetail;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
public class InvalidInputException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Map<String, List<ErrorDetail>> validationError;

    public InvalidInputException(HttpStatus httpStatus, Map<String, List<ErrorDetail>> validationErrorList) {
        super();
        this.httpStatus = httpStatus;
        this.validationError = validationErrorList;
    }
}
