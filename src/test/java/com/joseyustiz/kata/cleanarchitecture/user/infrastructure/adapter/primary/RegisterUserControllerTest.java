package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.adapter.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseyustiz.kata.cleanarchitecture.user.core.domain.UserName;
import com.joseyustiz.kata.cleanarchitecture.user.core.domain.UserNameUsedConstraintException;
import com.joseyustiz.kata.cleanarchitecture.user.core.port.primary.RegisterUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegisterUserController.class)
public class RegisterUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterUserUseCase userUseCase;

    @Test
    void invalidParameter_return400() throws Exception {
        RegisterUserController.RegisterUserRequest request = RegisterUserController.RegisterUserRequest.builder().build();
        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().is(400));
    }

    @Test
    void alreadyUserNameUsed_return400() throws Exception {
        String userName = "userName";

        Mockito.doThrow(new UserNameUsedConstraintException("", new UserName(userName))).when(userUseCase).handle(any());

        RegisterUserController.RegisterUserRequest request = RegisterUserController.RegisterUserRequest.builder()
                .userName(userName).fullName("full name")
                .email("email@example.com").password("P@ssw0rd.1")
                .build();

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().is(400));
    }

    @Test
    void validUsed_return201() throws Exception {
        String userName = "userName";
        RegisterUserController.RegisterUserRequest request = RegisterUserController.RegisterUserRequest.builder()
                .userName(userName).fullName("full name")
                .email("email@example.com").password("P@ssw0rd.1")
                .build();

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().is(201));
    }

}
