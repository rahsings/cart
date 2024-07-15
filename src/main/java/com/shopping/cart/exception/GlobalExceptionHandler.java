package com.shopping.cart.exception;

import com.shopping.cart.model.response.ErrorResponse;
import com.shopping.cart.model.validator.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidInputException(InvalidInputException ex) {
        return new ErrorResponse(ex.getHttpStatus(),ex.getValidationError());
    }

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCartNotFoundException(CartNotFoundException ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("CART_ERROR", Collections.singletonList(new ErrorDetail(ex.getCode(),ex.getMessage())));
        return new ErrorResponse(ex.getHttpStatus(),errorsMap);
    }

    @ExceptionHandler(CartValueInSufficientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCartValueInSufficientException(CartValueInSufficientException ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("CART_QUANTITY_ERROR", Collections.singletonList(new ErrorDetail(ex.getCode(),ex.getMessage())));
        return new ErrorResponse(ex.getHttpStatus(),errorsMap);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("PRODUCT_ERROR", Collections.singletonList(new ErrorDetail(ex.getCode(),ex.getMessage())));
        return new ErrorResponse(ex.getHttpStatus(),errorsMap);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("USER_ERROR", Collections.singletonList(new ErrorDetail(ex.getCode(),ex.getMessage())));
        return new ErrorResponse(ex.getHttpStatus(),errorsMap);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAddressNotFoundException(AddressNotFoundException ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("ADDRESS_ERROR", Collections.singletonList(new ErrorDetail(ex.getCode(),ex.getMessage())));
        return new ErrorResponse(ex.getHttpStatus(),errorsMap);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJsonProcessException(JsonParseException ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("JSON_ERROR", Collections.singletonList(new ErrorDetail(ex.getCode(),ex.getMessage())));
        return new ErrorResponse(ex.getStatus(),errorsMap);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnCheckedException(Exception ex) {
        Map<String, List<ErrorDetail>> errorsMap = new HashMap<>();
        errorsMap.put("INTERNAL_ERROR", Collections.singletonList(new ErrorDetail(ex.getLocalizedMessage(),ex.getMessage())));
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorsMap);
    }
}
