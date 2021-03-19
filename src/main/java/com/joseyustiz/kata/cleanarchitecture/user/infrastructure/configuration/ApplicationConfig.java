package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.configuration;

import com.joseyustiz.kata.cleanarchitecture.user.core.RegisterUserService;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.primary.RegisterUserUseCase;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.GetUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.RegisterUserPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public RegisterUserUseCase registerUserService(GetUserPort getUserPort, RegisterUserPort registerUserPort) {
        return new RegisterUserService(getUserPort, registerUserPort);
    }
}
