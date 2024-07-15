package com.shopping.cart.service.impl;

import com.shopping.cart.exception.InvalidInputException;
import com.shopping.cart.model.validator.ErrorDetail;
import com.shopping.cart.validator.ValidationErrors;
import com.shopping.cart.validator.Validator;
import com.shopping.cart.validator.ValidatorFactory;
import com.shopping.cart.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final ValidatorFactory validatorFactory;

    public <T> void validateObject(Class<T> type, T object) throws InvalidInputException {
        Validator<T> validator = validatorFactory.getValidator(type);
        ValidationErrors validationErrors = validator.validate(object);
        findAndThrowError(validationErrors);
    }

    private void findAndThrowError(ValidationErrors validationResults) throws InvalidInputException {
        Map<String, List<ErrorDetail>> validationErrorList = validationResults.getErrorMessageForField();
        if (!validationErrorList.isEmpty()) {
            throw new InvalidInputException(HttpStatus.BAD_REQUEST, validationErrorList);
        }
    }
}
