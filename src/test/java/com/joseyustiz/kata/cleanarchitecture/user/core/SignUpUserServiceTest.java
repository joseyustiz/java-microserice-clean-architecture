package com.joseyustiz.kata.cleanarchitecture.user.core;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.*;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.GetUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.RegisterUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.primary.RegisterUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SignUpUserServiceTest {
    GetUserPort getUserPort = Mockito.mock(GetUserPort.class);
    RegisterUserPort registerUserPort = Mockito.mock(RegisterUserPort.class);
    RegisterUserUseCase userCase = new RegisterUserService(getUserPort, registerUserPort);

    @Test
    void nullRegisterUserCommand_throwsIllegalArgumentException() {
        //given
        RegisterUserUseCase.RegisterUserCommand command = null;
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

    @Test
    void alreadyUserNameUsed_throwsUsedUserNameException() {
        //given
        UserName existingUserName = new UserName("userName");
        Password password = new Password("P@ssw0rd.1");
        FullName fullName = new FullName("Full Name");
        Email email = new Email("email@example.com");

        RegisterUserUseCase.RegisterUserCommand command = RegisterUserUseCase.RegisterUserCommand.builder()
                .userName(existingUserName)
                .email(email)
                .password(password)
                .fullName(fullName)
                .build();

        User user = User.builder().userName(existingUserName).password(password).email(email).fullName(fullName).build();

        Mockito.when(getUserPort.findByUserName(any(UserName.class))).thenReturn(Optional.of(user));

        //expect
        UserNameUsedConstraintException e = Assertions.assertThrows(UserNameUsedConstraintException.class, () -> userCase.handle(command));

        //then
        assertThat(e.getMessage()).isEqualTo("userName is already used by another user");

    }
    @Test
    void validUsed_isRegistered() {
        //given
        UserName userName = new UserName("userName");
        Password password = new Password("P@ssw0rd.1");
        FullName fullName = new FullName("Full Name");
        Email email = new Email("email@example.com");

        RegisterUserUseCase.RegisterUserCommand command = RegisterUserUseCase.RegisterUserCommand.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .fullName(fullName)
                .build();

        User user = User.builder().userName(userName).password(password).email(email).fullName(fullName).build();

        Mockito.when(getUserPort.findByUserName(any(UserName.class))).thenReturn(Optional.empty());

        //when
        userCase.handle(command);

        //then
        verify(getUserPort, times(1)).findByUserName(userName);
        verify(registerUserPort, times(1)).register(user);

    }
}
