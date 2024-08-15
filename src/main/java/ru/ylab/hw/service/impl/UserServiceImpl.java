package ru.ylab.hw.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.repository.UserRepository;
import ru.ylab.hw.service.UserService;
import ru.ylab.hw.dto.User;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String username, String password, Role role) {
        userRepository.save(new User(username, password, role));
        log.info("User with name {} registered", username);
    }

    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        log.info("User logged in: {}", username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public List<User> viewUsers() {
        return userRepository.getAllUsers();
    }
}
