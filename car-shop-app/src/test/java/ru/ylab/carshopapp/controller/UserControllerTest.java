package ru.ylab.carshopapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ylab.carshopapp.BaseTest;
import ru.ylab.carshopapp.dto.UserDTO;
import ru.ylab.carshopapp.entity.User;
import ru.ylab.carshopapp.enums.Role;
import ru.ylab.carshopapp.sequrity.BlacklistService;
import ru.ylab.carshopapp.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BlacklistService blacklistService;

    private UserDTO userDTO;
    private User user;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = UserDTO.builder()
                .username("walter")
                .password("password123")
                .role(Role.CLIENT)
                .build();

        user = User.builder()
                .username("walter")
                .password("password123")
                .role(Role.CLIENT)
                .build();

        token = "sampleToken";
    }

    @Test
    void viewUsers_ShouldReturnOk() throws Exception {
        List<User> users = Collections.singletonList(user);
        when(userService.viewUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(user.getUsername()));
    }

    @Test
    void getUserByUsername_ShouldReturnOk() throws Exception {
        when(userService.getUserByUsername(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/users/{username}", user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.role").value(user.getRole().toString()));
    }

    @Test
    void getUserByUsername_ShouldReturnNotFound() throws Exception {
        when(userService.getUserByUsername(anyString())).thenThrow(new NoSuchElementException("User not found"));

        mockMvc.perform(get("/api/users/{username}", "unknown_user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found: unknown_user"));
    }

    @Test
    void login_ShouldReturnOk() throws Exception {
        when(userService.login(anyString(), anyString())).thenReturn(token);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"walter\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    void login_ShouldReturnBadRequest() throws Exception {
        when(userService.login(anyString(), anyString())).thenThrow(new IllegalArgumentException("Invalid credentials"));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"walter\",\"password\":\"wrong_password\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void register_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"walter\",\"password\":\"password123\",\"role\":\"CLIENT\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void register_ShouldReturnBadRequest() throws Exception {
        when(userService.register(anyString(), anyString(), any(Role.class)))
                .thenThrow(new IllegalArgumentException("Registration failed"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"walter\",\"password\":\"short\",\"role\":\"CLIENT\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Registration failed"));
    }

    @Test
    void logout_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/users/logout")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out successfully"));
    }

    @Test
    void logout_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/users/logout")
                        .header(HttpHeaders.AUTHORIZATION, "InvalidToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid token"));
    }
}