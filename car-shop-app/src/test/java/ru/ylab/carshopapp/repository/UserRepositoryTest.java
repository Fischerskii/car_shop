package ru.ylab.carshopapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ylab.carshopapp.BaseTest;
import ru.ylab.carshopapp.entity.User;
import ru.ylab.carshopapp.enums.Role;
import ru.ylab.carshopapp.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest extends BaseTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @BeforeEach
    public void setup() {
        // Очистка всех пользователей перед каждым тестом
        userRepository.getAllUsers().forEach(user -> userRepository.delete(user.getUsername()));
    }

    @Test
    public void testSave() {
        User user = new User("testuser", "password123", Role.CLIENT);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.getUserByUsername("testuser");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testUpdate() {
        User user = new User("testuser", "password123", Role.CLIENT);
        userRepository.save(user);

        User updatedUser = new User("testuser", "newpassword456", Role.ADMIN);
        userRepository.update(updatedUser);

        Optional<User> foundUser = userRepository.getUserByUsername("testuser");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPassword()).isEqualTo("newpassword456");
        assertThat(foundUser.get().getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    public void testDelete() {
        User user = new User("testuser", "password123", Role.CLIENT);
        userRepository.save(user);

        userRepository.delete("testuser");

        Optional<User> foundUser = userRepository.getUserByUsername("testuser");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void testGetAllUsers() {
        userRepository.save(new User("user1", "password1", Role.CLIENT));
        userRepository.save(new User("user2", "password2", Role.ADMIN));

        List<User> users = userRepository.getAllUsers();
        assertThat(users).hasSize(2);
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User("testuser", "password123", Role.CLIENT);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.getUserByUsername("testuser");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testGetUserByUsername_NotFound() {
        Optional<User> user = userRepository.getUserByUsername("nonexistentuser");
        assertThat(user).isNotPresent();
    }
}