package ru.ylab.hw.repository.impl;

import ru.ylab.hw.config.DatabaseConfig;
import ru.ylab.hw.dto.User;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.exception.DataAccessException;
import ru.ylab.hw.repository.AbstractRepository;
import ru.ylab.hw.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    @Override
    public void save(User user) {
        String query = "INSERT INTO entity_schema.user (username, password, role) VALUES (?, ?, ?)";
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole().name());
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new DataAccessException("Failed to save user: " + user.getUsername(), e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE entity_schema.user SET password = ?, role = ? WHERE username = ?";
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getRole().name());
                statement.setString(3, user.getUsername());
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new DataAccessException("Failed to update user: " + user.getUsername(), e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void delete(String username) {
        String query = "DELETE FROM entity_schema.user WHERE username = ?";
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new DataAccessException("Failed to delete user: " + username, e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM entity_schema.user WHERE username = ?";
        User user = null;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role").toUpperCase().trim());
                user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve user by username: " + username, e);
        }
        return user;
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
