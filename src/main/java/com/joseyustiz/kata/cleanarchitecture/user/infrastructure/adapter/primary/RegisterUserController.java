package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.adapter.primary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.*;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.primary.RegisterUserUseCase;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RegisterUserController {
    private final RegisterUserUseCase userUseCase;

    @PostMapping("/user/signup")
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
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return new ResponseEntity<>("error occurred", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UserNameUsedConstraintException.class})
    public ResponseEntity<Object> handleUserNameUsedConstraintException(final UserNameUsedConstraintException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
