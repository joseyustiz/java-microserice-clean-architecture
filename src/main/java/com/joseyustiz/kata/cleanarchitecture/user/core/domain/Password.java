package com.joseyustiz.kata.cleanarchitecture.user.core.domain;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class Password {
    @NotNull(message = "must not be null")
    /*
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{10,}             # anything, at least ten places though
     * $                 # end-of-string
     */
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$" ,
            message = "must have a digit, a lower case letter, an upper case letter and a special character at least once, no whitespace allowed and at least ten characters")
    String value;
}
