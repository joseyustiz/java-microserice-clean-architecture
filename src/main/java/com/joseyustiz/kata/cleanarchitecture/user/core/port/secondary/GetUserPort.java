package com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.User;
import com.joseyustiz.kata.cleanarchitecture.user.core.domain.UserName;

import java.util.Optional;

public interface GetUserPort {
    Optional<User> findByUserName(UserName userName);
}
