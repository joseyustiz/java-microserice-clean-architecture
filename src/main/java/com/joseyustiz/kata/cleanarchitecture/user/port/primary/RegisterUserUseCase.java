package com.joseyustiz.kata.cleanarchitecture.user.port.primary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface RegisterUserUseCase {
    void handle(RegisterUserCommand command);

    @EqualsAndHashCode(callSuper = false)
    @Value
    @Builder
    class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {
        @NotNull
        @Valid
        UserName userName;
        @NotNull
        @Valid
        Password password;
        @NotNull
        @Valid
        FullName fullName;
        @NotNull
        @Valid
        Email email;

        public RegisterUserCommand(UserName userName, Password password, FullName fullName, Email email) {
            this.userName = userName;
            this.password = password;
            this.fullName = fullName;
            this.email = email;
            this.validateSelf();
        }
    }
}
