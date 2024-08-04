package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl();
    }

    @Test
    public void testRegisterAndLogin() {
        userService.register("admin", "admin123", User.Role.ADMIN);
        User user = userService.login("admin", "admin123");
        assertNotNull(user);
        assertThat(user.getRole()).isEqualTo(User.Role.ADMIN);
    }

    @Test
    public void testInvalidLogin() {
        userService.register("admin", "admin123", User.Role.ADMIN);
        User user = userService.login("admin", "wrongpassword");
        assertNull(user);
    }
}