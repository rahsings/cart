package com.shopping.cart.validator;

import com.shopping.cart.exception.ValidatorNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidatorFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public <T> Validator<T> getValidator(Class<T> type) {
        ResolvableType resolvableType = ResolvableType.forClassWithGenerics(Validator.class, type);
        String[] beanNames = context.getBeanNamesForType(resolvableType);

        if (beanNames.length == 0) {
            throw new ValidatorNotFoundException(HttpStatus.NOT_IMPLEMENTED, "No validator found for type: " + type.getName(), "VALIDATOR NOT FOUND");
        }
        return (Validator<T>) context.getBean(beanNames[0]);
    }
}