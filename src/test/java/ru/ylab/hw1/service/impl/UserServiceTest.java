package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldSaveUser() {
        String username = "Pavel";
        String password = "password";
        User.Role role = User.Role.CLIENT;

        userService.register(username, password, role);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login_ShouldReturnUserWhenCredentialsAreCorrect() {
        String username = "Pavel";
        String password = "password";
        User user = new User(username, password, User.Role.CLIENT);
        Map<String, User> users = new HashMap<>();
        users.put(username, user);
        when(userRepository.findAll()).thenReturn(users);

        User result = userService.login(username, password);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void login_ShouldReturnNullWhenCredentialsAreIncorrect() {
        String username = "Pavel";
        String password = "wrong_password";
        User user = new User(username, "password", User.Role.CLIENT);
        Map<String, User> users = new HashMap<>();
        users.put(username, user);
        when(userRepository.findAll()).thenReturn(users);

        User result = userService.login(username, password);

        assertThat(result).isNull();
    }

    @Test
    void viewUsers_ShouldPrintAllUsers() {
        User user1 = new User("Pavel", "password", User.Role.CLIENT);
        User user2 = new User("Sid", "password", User.Role.CLIENT);
        Map<String, User> users = new HashMap<>();
        users.put(user1.getUsername(), user1);
        users.put(user2.getUsername(), user2);
        when(userRepository.findAll()).thenReturn(users);

        userService.viewUsers();

        verify(userRepository, times(1)).findAll();
        assertThat(userRepository.findAll()).containsValues(user1, user2);
    }
}