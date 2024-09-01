package ru.ylab.carshopapp.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.carshopapp.entity.User;
import ru.ylab.carshopapp.enums.Role;
import ru.ylab.carshopapp.exception.DataAccessException;
import ru.ylab.carshopapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO entity_schema.user (username, password, role) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.getRole().name());
            log.info("User with username {} has been saved", user.getUsername());
        } catch (DataAccessException e) {
            log.error("Error creating user with username: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE entity_schema.user SET password = ?, role = ? WHERE username = ?";
        try {
            jdbcTemplate.update(query, user.getPassword(), user.getRole().name(), user.getUsername());
            log.info("User with username {} has been updated", user.getUsername());
        } catch (DataAccessException e) {
            log.error("Error updating user with username: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Override
    public void delete(String username) {
        String query = "DELETE FROM entity_schema.user WHERE username = ?";
        try {
            jdbcTemplate.update(query, username);
            log.info("User with username {} has been deleted", username);
        } catch (DataAccessException e) {
            log.error("Error deleting user with username: {}", username, e);
            throw e;
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        String query = "SELECT * FROM entity_schema.user WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(query, new Object[]{username}, (rs, rowNum) ->
                    new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            Role.valueOf(rs.getString("role").toUpperCase().trim())
                    ));
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            log.error("Failed to retrieve user by username: {}", username, e);
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM entity_schema.user";
        return jdbcTemplate.query(query, (rs, rowNum) ->
                new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role").toUpperCase().trim())
                ));
    }
}
