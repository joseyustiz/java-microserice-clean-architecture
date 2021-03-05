package com.joseyustiz.kata.cleanarchitecture.user;

import javax.validation.*;
import java.util.Set;

public class ValidationHelper {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validateSelf(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
