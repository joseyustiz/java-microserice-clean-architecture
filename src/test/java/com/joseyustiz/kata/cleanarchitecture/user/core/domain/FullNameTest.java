package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import com.joseyustiz.kata.cleanarchitecture.user.ValidationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolationException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FullNameTest {

    private static Stream<Arguments> provideInvalidFullNames() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("", 2),
                Arguments.of(" ",2),
                Arguments.of("12",1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidFullNames")
    void invalidFullName_throwsException(String fullNameValue, int numberOfViolations) {
        FullName fullName = new FullName(fullNameValue);
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> ValidationHelper.validateSelf(fullName));
        assertThat(e.getConstraintViolations()).hasSize(numberOfViolations);
    }
}