package ru.ylab.carshopapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ylab.auditlogging.annotation.Audit;
import ru.ylab.common.enums.ActionType;
import ru.ylab.carshopapp.enums.Role;
import ru.ylab.carshopapp.repository.UserRepository;
import ru.ylab.carshopapp.sequrity.JwtUtil;
import ru.ylab.carshopapp.service.UserService;
import ru.ylab.carshopapp.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@Audit(actionType = ActionType.USER_ACTIONS)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, String password, Role role) {
        userRepository.save(new User(username, password, role));
        log.info("User with name {} registered", username);
    }

    @Override
    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.getUserByUsername(username);

        User user = optionalUser.filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> {
                    log.info("Login failed for user: {}", username);
                    return new IllegalArgumentException("Invalid username or password");
                });

        List<String> roles = Collections.singletonList(user.getRole().name());
        return JwtUtil.generateToken(username, roles);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + username));
    }

    @Override
    public List<User> viewUsers() {
        return userRepository.getAllUsers();
    }
}
