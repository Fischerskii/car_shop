package ru.ylab.hw1.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ylab.hw1.config.DatabaseConfig;
import ru.ylab.hw1.dto.UserDTO;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exception.DataAccessException;
import ru.ylab.hw1.repository.AbstractRepository;
import ru.ylab.hw1.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    @Override
    public void save(UserDTO userDTO) {
        String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userDTO.getUsername());
                statement.setString(2, userDTO.getPassword());
                statement.setString(3, userDTO.getRole().name());
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new DataAccessException("Failed to save user: " + userDTO.getUsername(), e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(UserDTO userDTO) {
        String query = "UPDATE user SET password = ?, role = ? WHERE username = ?";
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userDTO.getPassword());
                statement.setString(2, userDTO.getRole().name());
                statement.setString(3, userDTO.getUsername());
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection);
            throw new DataAccessException("Failed to update user: " + userDTO.getUsername(), e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void delete(String username) {
        String query = "DELETE FROM user WHERE username = ?";
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
    public UserDTO getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        UserDTO userDTO = null;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userDTO = new UserDTO(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve user by username: " + username, e);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        String query = "SELECT * FROM user";
        List<UserDTO> userDTOS = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                userDTOS.add(new UserDTO(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all users", e);
        }
        return userDTOS;
    }
}
