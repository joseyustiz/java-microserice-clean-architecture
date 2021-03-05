package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class FullName {
    @NotBlank
    @Size(min = 3)
    String value;
}
