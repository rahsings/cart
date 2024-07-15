package com.shopping.cart.validator;

import com.shopping.cart.exception.ValidatorNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidatorFactoryTest {

    private ValidatorFactory validatorFactory;
    private ApplicationContext mockApplicationContext;

    @BeforeEach
    void setUp() {
        validatorFactory = new ValidatorFactory();
        mockApplicationContext = mock(ApplicationContext.class);
        validatorFactory.setApplicationContext(mockApplicationContext);
    }

    @Test
    void test_ValidatorFound() {
        Class<String> type = String.class;
        Validator<String> mockValidator = mock(Validator.class);

        String[] beanNames = {"TestValidator"};
        when(mockApplicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(Validator.class, type)))
                .thenReturn(beanNames);
        when(mockApplicationContext.getBean("TestValidator")).thenReturn(mockValidator);

        Validator<String> validator = validatorFactory.getValidator(type);

        assertNotNull(validator);
        assertEquals(mockValidator, validator);
    }

    @Test
    void test_ValidatorNotFound() {
        Class<Object> type = Object.class;

        when(mockApplicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(Validator.class, type)))
                .thenReturn(new String[0]);

        Exception exception = assertThrows(ValidatorNotFoundException.class, () -> validatorFactory.getValidator(type));

        assertEquals("No validator found for type: java.lang.Object", exception.getMessage());
    }
}
