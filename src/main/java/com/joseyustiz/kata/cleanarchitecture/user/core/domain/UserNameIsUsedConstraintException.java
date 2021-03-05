package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class UserNameIsUsedConstraintException extends RuntimeException {
    private UserName userName;
    public UserNameIsUsedConstraintException(String message, UserName userName) {
        super(message);
        this.userName = userName;
    }
}
