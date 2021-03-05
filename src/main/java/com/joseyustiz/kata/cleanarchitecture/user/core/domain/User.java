package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    UserName userName;
    Email email;
    FullName fullName;
    Password password;
}
