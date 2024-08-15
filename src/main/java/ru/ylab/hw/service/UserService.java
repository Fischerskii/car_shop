package ru.ylab.hw.service;

import ru.ylab.hw.dto.User;
import ru.ylab.hw.enums.Role;

import java.util.List;

public interface UserService {

    /**
     * Registers a new user with the specified username, password, and role.
     * Logs the registration event.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param role     the role assigned to the new user
     */
    void register(String username, String password, Role role);

    /**
     * Logs in a user by checking their username and password.
     * Logs the login event.
     *
     * @param username the username of the user trying to log in
     * @param password the password provided by the user
     * @return the {@link User} object if the login is successful, or {@code null} if the credentials are incorrect
     */
    User login(String username, String password);

    /**
     * Retrieves a list of all users from the repository.
     *
     * @return a list of {@link User} objects representing all users
     */
    List<User> viewUsers();
}
