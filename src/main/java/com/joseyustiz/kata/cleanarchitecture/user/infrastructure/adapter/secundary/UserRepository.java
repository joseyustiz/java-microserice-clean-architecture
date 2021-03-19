package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.adapter.secundary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.*;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.GetUserPort;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary.RegisterUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
@AllArgsConstructor
@Component
public class UserRepository implements GetUserPort, RegisterUserPort {
    private final UserSpringRepository repository;
    @Override
    public Optional<User> findByUserName(UserName userName) {
        Optional<UserSpringRepository.UserDto> user = repository.findByUserName(userName.getValue());
        if(user.isPresent()){
            UserSpringRepository.UserDto dto = user.get();
            return Optional.of(User.builder().userName(new UserName(dto.getUserName()))
                    .email(new Email(dto.getEmail()))
                    .fullName( new FullName(dto.getFullName()))
                    .password( new Password(dto.getPassword())).build());
        }
        return Optional.empty();
    }

    @Override
    public void register(User user) {
        repository.save(new UserSpringRepository.UserDto(user));
    }
}
