package com.shopping.cart.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.shopping.cart.model.validator.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractValidatorTest {

    private ConcreteValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ConcreteValidator();
    }

    @Test
    void validate_ShouldReturnNull_WhenValueIsOne() {

        String key = "QUANTITY";
        Object value = 1;

        ValidationError error = validator.validate(value, key);

        assertNull(error);
    }

    @Test
    void validate_ShouldReturnValidationError_WhenValueIsNull() {

        String key = "QUANTITY";
        Object value = null;

        ValidationError error = validator.validate(value, key);

        assertNotNull(error);
        assertEquals("QUANTITY", error.getKey());
        assertEquals("Quantity value is either empty,null or 0", error.getCode());
    }

    @Test
    void validate_ShouldReturnValidationError_WhenValueIsZero() {

        String key = "QUANTITY";
        Object value = 0;

        ValidationError error = validator.validate(value, key);

        assertNotNull(error);
        assertEquals("QUANTITY", error.getKey());
        assertEquals("Quantity value is either empty,null or 0", error.getCode());
    }

    @Test
    void validate_ShouldReturnOk_WhenValueIsFive() {

        String key = "QUANTITY";
        Object value = 5;

        ValidationError error = validator.validate(value, key);

        assertNull(error);
    }

    private static class ConcreteValidator extends AbstractValidator<Object> {
        @Override
        public ValidationErrors validate(Object object) {
            ValidationErrors validationErrors = new ValidationErrors();
            validationErrors.add(validate(object, "QUANTITY"));
            return validationErrors;
        }
    }
}
