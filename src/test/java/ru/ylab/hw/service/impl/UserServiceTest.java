//package ru.ylab.hw1.service.impl;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.ylab.hw1.dto.User;
//import ru.ylab.hw1.enums.Role;
//import ru.ylab.hw1.repository.UserRepository;
//import ru.ylab.hw1.service.UserService;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User("test_user", "test_password", Role.CLIENT);
//    }
//
//    @Test
//    void saveUser_ShouldReturnSavedUser() {
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        userService.register("test_user", "test_password", Role.CLIENT);
//
//        assertThat(savedUser).isNotNull();
//        assertThat(savedUser.getUsername()).isEqualTo("testuser");
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    void findUserById_ShouldReturnUser_WhenUserExists() {
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//
//        Optional<User> foundUser = userService.findUserById(1);
//
//        assertThat(foundUser).isPresent();
//        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
//        verify(userRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void findUserById_ShouldReturnEmpty_WhenUserDoesNotExist() {
//        when(userRepository.findById(1)).thenReturn(Optional.empty());
//
//        Optional<User> foundUser = userService.findUserById(1);
//
//        assertThat(foundUser).isNotPresent();
//        verify(userRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void deleteUserById_ShouldDeleteUser_WhenUserExists() {
//        when(userRepository.existsById(1)).thenReturn(true);
//
//        userService.deleteUserById(1);
//
//        verify(userRepository, times(1)).deleteById(1);
//    }
//
//    @Test
//    void deleteUserById_ShouldThrowException_WhenUserDoesNotExist() {
//        when(userRepository.existsById(1)).thenReturn(false);
//
//        assertThatThrownBy(() -> userService.deleteUserById(1))
//                .isInstanceOf(UserNotFoundException.class)
//                .hasMessageContaining("User not found");
//
//        verify(userRepository, never()).deleteById(1);
//    }
//}
