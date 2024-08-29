package ru.ylab.hw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.ylab.hw.config.AppConfig;
import ru.ylab.hw.dto.UserDTO;
import ru.ylab.hw.entity.User;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.sequrity.BlacklistService;
import ru.ylab.hw.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@ContextConfiguration(classes = {AppConfig.class, UserController.class})
@WebAppConfiguration
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BlacklistService blacklistService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("View all users successfully")
    void testViewUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User("testUser1", "password1", Role.ADMIN);
        User user2 = new User("testUser2", "password2", Role.CLIENT);
        users.add(user1);
        users.add(user2);

        when(userService.viewUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.viewUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(users);
        verify(userService, times(1)).viewUsers();
    }

    @Test
    @DisplayName("Get user by username - user found")
    void testGetUserByUsername_Found() {
        String username = "testUser1";
        User user = new User(username, "password1", Role.ADMIN);

        when(userService.getUserByUsername(username)).thenReturn(user);

        ResponseEntity<?> response = userController.getUserByUsername(username);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
        verify(userService, times(1)).getUserByUsername(username);
    }

    @Test
    @DisplayName("Get user by username - user not found")
    void testGetUserByUsername_NotFound() {
        String username = "testUser";

        when(userService.getUserByUsername(username)).thenThrow(new NoSuchElementException());

        ResponseEntity<?> response = userController.getUserByUsername(username);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("User not found: " + username);
        verify(userService, times(1)).getUserByUsername(username);
    }

    @Test
    @DisplayName("Login successfully")
    void testLogin_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");
        userDTO.setRole(Role.ADMIN);

        String token = "token";

        when(userService.login(userDTO.getUsername(), userDTO.getPassword())).thenReturn(token);

        ResponseEntity<?> response = userController.login(userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(token);
        verify(userService, times(1)).login(userDTO.getUsername(), userDTO.getPassword());
    }

    @Test
    @DisplayName("Logout successfully")
    void testLogout_Success() {
        String token = "token";

        doNothing().when(blacklistService).blacklistToken(token);

        ResponseEntity<?> response = userController.logout("Bearer " + token);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Logged out successfully");
        verify(blacklistService, times(1)).blacklistToken(token);
    }
}