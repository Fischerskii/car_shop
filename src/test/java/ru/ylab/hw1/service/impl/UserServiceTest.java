package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.repository.impl.UserRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Проверка добавления/регистрации нового пользователя")
    void register_ShouldSaveUser() {
        String username = "Pavel";
        String password = "password";
        Role role = Role.CLIENT;

        userService.register(username, password, role);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Проверка работы авторизации")
    void login_ShouldReturnUserWhenCredentialsAreCorrect() {
        String username = "Pavel";
        String password = "password";
        User user = new User(username, password, Role.CLIENT);
        Map<String, User> users = new HashMap<>();
        users.put(username, user);
        when(userRepository.findAll()).thenReturn(users);

        User result = userService.login(username, password);

        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("Проверка на невалидный ввод пароля")
    void login_ShouldReturnNullWhenCredentialsAreIncorrect() {
        String username = "Pavel";
        String password = "wrong_password";
        User user = new User(username, "password", Role.CLIENT);
        Map<String, User> users = new HashMap<>();
        users.put(username, user);
        when(userRepository.findAll()).thenReturn(users);

        User result = userService.login(username, password);

        assertThat(result).isNull();
    }
}