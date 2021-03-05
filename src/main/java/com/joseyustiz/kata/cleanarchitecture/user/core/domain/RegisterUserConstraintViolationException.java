package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class RegisterUserConstraintViolationException extends ConstraintViolationException {

    public RegisterUserConstraintViolationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }
}
