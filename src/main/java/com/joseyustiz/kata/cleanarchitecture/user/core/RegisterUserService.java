package com.joseyustiz.kata.cleanarchitecture.user.core;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.User;
import com.joseyustiz.kata.cleanarchitecture.user.core.domain.UserNameIsUsedConstraintException;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.GetUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.RegisterUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.port.primary.RegisterUserUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {
    private final GetUserPort getUserPort;
    private final RegisterUserPort registerUserPort;
    @Override
    public void handle(@NonNull RegisterUserCommand command) {
        Optional<User> user = getUserPort.findByUserName(command.getUserName());
        user.ifPresent(u ->{throw new UserNameIsUsedConstraintException(String.format("%s is already used by another user",
                u.getUserName().getValue()), u.getUserName());});

        registerUserPort.register(User.builder()
                .userName(command.getUserName())
                .password(command.getPassword())
                .email(command.getEmail())
                .fullName(command.getFullName())
                .build());
    }
}
