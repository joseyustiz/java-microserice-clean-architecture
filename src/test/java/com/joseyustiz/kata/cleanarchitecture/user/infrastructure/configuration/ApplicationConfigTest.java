package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.configuration;

import com.joseyustiz.kata.cleanarchitecture.user.core.port.primary.RegisterUserUseCase;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.GetUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.RegisterUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.infrastructure.adapter.secundary.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationConfigTest {
    @Test
    void validDependencies_registerUserService() {
        ApplicationConfig config = new ApplicationConfig();
        GetUserPort mockedGetUserPort = Mockito.mock(GetUserPort.class);
        RegisterUserPort mockedRegisterUser = Mockito.mock(RegisterUserPort.class);
        RegisterUserUseCase service = config.registerUserService(mockedGetUserPort, mockedRegisterUser);

        assertThat(service).isNotNull();
    }
}
