package ru.ylab.hw.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw.BaseTest;
import ru.ylab.hw.entity.User;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.repository.UserRepository;
import ru.ylab.hw.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

class UserServiceTest extends BaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Register should save a new user in the repository")
    void register_ShouldSaveUser() {
        User user = new User("sid", "password123", Role.CLIENT);

        userService.register(user.getUsername(), user.getPassword(), user.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Login should return a token when credentials are correct")
    void login_ShouldReturnToken_WhenCredentialsAreCorrect() {
        User user = new User("sid", "password123", Role.CLIENT);
        when(userRepository.getUserByUsername("sid")).thenReturn(Optional.of(user));

        String token = userService.login("sid", "password123");

        assertThat(token).isNotNull();
        verify(userRepository, times(1)).getUserByUsername("sid");
    }

    @Test
    @DisplayName("Login should throw exception when credentials are incorrect")
    void login_ShouldThrowException_WhenCredentialsAreIncorrect() {
        when(userRepository.getUserByUsername("sid")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login("sid", "wrongpassword"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid username or password");

        verify(userRepository, times(1)).getUserByUsername("sid");
    }

    @Test
    @DisplayName("Get user by username should return user if it exists")
    void getUserByUsername_ShouldReturnUser_WhenUserExists() {
        User user = new User("sid", "password123", Role.CLIENT);
        when(userRepository.getUserByUsername("sid")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("sid");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("sid");
        verify(userRepository, times(1)).getUserByUsername("sid");
    }

    @Test
    @DisplayName("Get user by username should throw exception when user does not exist")
    void getUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.getUserByUsername("sid")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByUsername("sid"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User not found: sid");

        verify(userRepository, times(1)).getUserByUsername("sid");
    }

    @Test
    @DisplayName("View users should return all users from the repository")
    void viewUsers_ShouldReturnAllUsers() {
        List<User> users = Arrays.asList(
                new User("sid", "password123", Role.CLIENT),
                new User("walker", "password456", Role.ADMIN)
        );
        when(userRepository.getAllUsers()).thenReturn(users);

        List<User> allUsers = userService.viewUsers();

        assertThat(allUsers).hasSize(2);
        assertThat(allUsers.get(0).getUsername()).isEqualTo("sid");
        assertThat(allUsers.get(1).getUsername()).isEqualTo("walker");
        verify(userRepository, times(1)).getAllUsers();
    }
}
