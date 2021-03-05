package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
@Getter
public class UserNameUsedConstraintException extends RuntimeException {
    private UserName userName;
    public UserNameUsedConstraintException(String message, UserName userName) {
        super(message);
        this.userName = userName;
    }
}
