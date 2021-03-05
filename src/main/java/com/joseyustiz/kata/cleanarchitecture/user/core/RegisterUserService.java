package com.joseyustiz.kata.cleanarchitecture.user.core;

import com.joseyustiz.kata.cleanarchitecture.user.port.primary.RegisterUserUseCase;
import lombok.NonNull;

public class RegisterUserService implements RegisterUserUseCase {

    @Override
    public void handle(@NonNull RegisterUserCommand command) {

    }
}
