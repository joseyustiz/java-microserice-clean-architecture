package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.adapter.primary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.*;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.primary.RegisterUserUseCase;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RegisterUserController {
    private final RegisterUserUseCase userUseCase;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterUserRequest request) {
        userUseCase.handle(RegisterUserUseCase.RegisterUserCommand.builder()
                .email(new Email(request.getEmail()))
                .userName(new UserName(request.getUserName()))
                .fullName(new FullName(request.getUserName()))
                .password(new Password(request.getPassword()))
                .build());

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.error("error", ex);
        return new ResponseEntity<>("error occurred", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UserNameUsedConstraintException.class})
    public ResponseEntity<Object> handleUserNameUsedConstraintException(final UserNameUsedConstraintException ex) {
        log.error("error", ex);
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        final List<String> errors = new ArrayList<>();

        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField()+":"+ error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName()+":"+ error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        Stream<String> errors = ex.getConstraintViolations().stream().map(e -> e.getPropertyPath() + ":" + e.getMessage());
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class RegisterUserRequest {
        @NotBlank
        private String userName;
        @NotBlank
        private String password;
        @NotBlank
        private String fullName;
        @NotBlank
        private String email;
    }
}
