package ru.ylab.hw.repository;

import ru.ylab.hw.dto.User;
import ru.ylab.hw.exception.DataAccessException;

import java.util.List;

/**
 * Implementation of the {@link UserRepository} interface for managing users in the database.
 */
public interface UserRepository {

    /**
     * Saves the specified user to the database.
     * If an error occurs during the operation, the transaction is rolled back.
     *
     * @param user the user to be saved
     * @throws DataAccessException if an error occurs during the database operation
     */
    void save(User user);

    /**
     * Retrieves a user by username from the database.
     *
     * @param username the username of the user to be retrieved
     * @return the user corresponding to the given username, or {@code null} if not found
     * @throws DataAccessException if an error occurs during the database operation
     */
    User getUserByUsername(String username);

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users in the database
     * @throws DataAccessException if an error occurs during the database operation
     */
    List<User> getAllUsers();

    /**
     * Updates the details of an existing user in the database.
     * If an error occurs during the operation, the transaction is rolled back.
     *
     * @param user the user with updated details
     * @throws DataAccessException if an error occurs during the database operation
     */
    void update(User user);

    /**
     * Deletes a user from the database by username.
     * If an error occurs during the operation, the transaction is rolled back.
     *
     * @param username the username of the user to be deleted
     * @throws DataAccessException if an error occurs during the database operation
     */
    void delete(String username);
}
