package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import com.joseyustiz.kata.cleanarchitecture.user.ValidationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolationException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordTest {

    private static final String PASSWORD_COMPLEXITY_CONSTRAIN_MESSAGE = "must have a digit, a lower case letter, an upper case letter and a special character at least once, no whitespace allowed and at least ten characters";

    private static Stream<Arguments> provideInvalidPassword() {
        return Stream.of(
                Arguments.of(null, 1, "must not be null"),
                Arguments.of("", 1, PASSWORD_COMPLEXITY_CONSTRAIN_MESSAGE),
                Arguments.of(" ", 1, PASSWORD_COMPLEXITY_CONSTRAIN_MESSAGE),
                Arguments.of("P@ssword1", 1, PASSWORD_COMPLEXITY_CONSTRAIN_MESSAGE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPassword")
    void invalidPassword_throwsException(String passwordValue, int numberOfViolations, String message) {
        Password password = new Password(passwordValue);
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class, () -> ValidationHelper.validateSelf(password));
        assertThat(e.getConstraintViolations()).hasSize(numberOfViolations);
        assertThat(e.getConstraintViolations().stream().findFirst().get().getMessage()).isEqualTo(message);

    }
}
