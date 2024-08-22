package ru.ylab.hw.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw.config.DatabaseConfig;
import ru.ylab.hw.entity.User;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.exception.DataAccessException;
import ru.ylab.hw.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {
        String query = "INSERT INTO entity_schema.user (username, password, role) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole().name());
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Transaction rolled back", e);
            }
        } catch (SQLException e) {
            log.error("Error creating user with username: {}", user.getUsername(), e);
            throw new DataAccessException("Failed to save user: " + user.getUsername(), e);
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE entity_schema.user " +
                "SET password = ?, role = ? " +
                "WHERE username = ?";

        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getRole().name());
                statement.setString(3, user.getUsername());
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Transaction rolled back", e);
            }
        } catch (SQLException e) {
            log.error("Error updating user with username: {}", user.getUsername(), e);
            throw new DataAccessException("Failed to update user: " + user.getUsername(), e);
        }
    }

    @Override
    public void delete(String username) {
        String query = "DELETE FROM entity_schema.user " +
                "WHERE username = ?";

        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Transaction rolled back", e);
            }
        } catch (SQLException e) {
            log.error("Error deleting user with username: {}", username, e);
            throw new DataAccessException("Failed to delete user: " + username, e);
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        String query = "SELECT * FROM entity_schema.user " +
                "WHERE username = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role").toUpperCase().trim());
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve user by username: " + username, e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM entity_schema.user";
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all users", e);
        }
        return users;
    }
}
