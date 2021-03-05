package com.joseyustiz.kata.cleanarchitecture.user.core.port.secondary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.User;

public interface RegisterUserPort {
    void register(User user);
}
