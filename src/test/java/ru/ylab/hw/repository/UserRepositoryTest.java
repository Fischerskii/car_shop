package ru.ylab.hw.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.hw.BaseTest;
import ru.ylab.hw.entity.User;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.repository.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends BaseTest {

    private Connection connection;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
        connection.setAutoCommit(false);
        userRepository = new UserRepositoryImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("Save user should persist the user in the database")
    void save_ShouldPersistUser() {
        User user = new User("sid", "password123", Role.CLIENT);

        userRepository.save(user);

        Optional<User> foundUser = userRepository.getUserByUsername("sid");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("sid");
    }

    @Test
    @DisplayName("Update user should modify user details in the database")
    void update_ShouldUpdateUserDetails() {
        User user = new User("sid", "password123", Role.CLIENT);
        userRepository.save(user);

        user.setPassword("newpassword");
        userRepository.update(user);

        Optional<User> updatedUser = userRepository.getUserByUsername("sid");

        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getPassword()).isEqualTo("newpassword");
    }

    @Test
    @DisplayName("Delete user should remove the user from the database")
    void delete_ShouldRemoveUser() {
        User user = new User("sid", "password123", Role.CLIENT);
        userRepository.save(user);

        userRepository.delete(user.getUsername());

        Optional<User> deletedUser = userRepository.getUserByUsername("sid");

        assertThat(deletedUser).isEmpty();
    }

    @Test
    @DisplayName("Get user by username should return user if it exists")
    void getUserByUsername_ShouldReturnUser_WhenUserExists() {
        User user = new User("sid", "password123", Role.CLIENT);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.getUserByUsername("sid");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("sid");
    }

    @Test
    @DisplayName("Get all users should return all persisted users")
    void getAllUsers_ShouldReturnAllPersistedUsers() {
        User user1 = new User("sid", "password123", Role.CLIENT);
        User user2 = new User("walker", "password456", Role.ADMIN);
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.getAllUsers();

        assertThat(users).hasSize(2);
        assertThat(users.get(0).getUsername()).isEqualTo("sid");
        assertThat(users.get(1).getUsername()).isEqualTo("walker");
    }
}