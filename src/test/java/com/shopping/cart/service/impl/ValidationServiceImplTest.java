package com.shopping.cart.service.impl;

import com.shopping.cart.exception.InvalidInputException;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.model.validator.ErrorDetail;
import com.shopping.cart.validator.ValidationErrors;
import com.shopping.cart.validator.Validator;
import com.shopping.cart.validator.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationServiceImplTest {

    @Mock
    private ValidatorFactory validatorFactory;

    @Mock
    private Validator<Object> validator;

    @Mock
    private ValidationErrors validationErrors;

    @InjectMocks
    private ValidationServiceImpl validationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_validateObject_noErrors() throws InvalidInputException {
        Object testObject = new Object();

        when(validatorFactory.getValidator(Object.class)).thenReturn(validator);
        when(validator.validate(testObject)).thenReturn(validationErrors);
        when(validationErrors.getErrorMessageForField()).thenReturn(Collections.emptyMap());

        assertDoesNotThrow(() -> validationService.validateObject(Object.class, testObject));
    }

    @Test
    void test_validateObject_withErrors() {
        Object testObject = new Object();
        Map<String, List<ErrorDetail>> errors = Map.of("field", List.of(new ErrorDetail(ErrorCode.CART_ID.getKey(), ErrorCode.CART_ID.getMessage())));

        when(validatorFactory.getValidator(Object.class)).thenReturn(validator);
        when(validator.validate(testObject)).thenReturn(validationErrors);
        when(validationErrors.getErrorMessageForField()).thenReturn(errors);

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> validationService.validateObject(Object.class, testObject));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(errors, exception.getValidationError());
    }
}

