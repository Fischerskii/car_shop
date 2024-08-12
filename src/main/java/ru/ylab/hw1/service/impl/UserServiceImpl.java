package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.repository.UserRepository;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.dto.User;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface that provides
 * methods for user registration, login, and retrieval of user information.
 */
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * @param userRepository the user repository to interact with the data source
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user with the specified username, password, and role.
     * Logs the registration event.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param role     the role assigned to the new user
     */
    public void register(String username, String password, Role role) {
        userRepository.save(new User(username, password, role));
        log.info("User with name {} registered", username);
    }

    /**
     * Logs in a user by checking their username and password.
     * Logs the login event.
     *
     * @param username the username of the user trying to log in
     * @param password the password provided by the user
     * @return the {@link User} object if the login is successful, or {@code null} if the credentials are incorrect
     */
    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        log.info("User logged in: {}", username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    /**
     * Retrieves a list of all users from the repository.
     *
     * @return a list of {@link User} objects representing all users
     */
    public List<User> viewUsers() {
        return userRepository.getAllUsers();
    }
}
