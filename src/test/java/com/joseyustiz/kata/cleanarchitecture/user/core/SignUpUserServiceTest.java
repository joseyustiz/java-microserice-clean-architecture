package com.joseyustiz.kata.cleanarchitecture.user.core;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.*;
import com.joseyustiz.kata.cleanarchitecture.user.port.primary.RegisterUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolationException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SignUpUserServiceTest {
    @Test
    void nullRegisterUserCommand_throwsIllegalArgumentException() {
        //given
        RegisterUserUseCase.RegisterUserCommand command = null;
        RegisterUserUseCase userCase = new RegisterUserService();
        //expect
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> userCase.handle(command));
        //then
        assertThat(e.getMessage()).isNotBlank();
    }

    @Test
    void invalidValuesForRegisterUserCommand_throwsSpecificException() {
        //expect
        ConstraintViolationException e = Assertions.assertThrows(ConstraintViolationException.class,
                () -> RegisterUserUseCase.RegisterUserCommand.builder().userName(null)
                .password(null).fullName(null).email(null).build());
        //then
        assertThat(e.getConstraintViolations()).hasSize(4);
    }
}
