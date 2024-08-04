package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.datasource.UserRepository;
import ru.ylab.hw1.datasource.impl.UserRepositoryImpl;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.DuplicateException;
import ru.ylab.hw1.service.AuthService;

import java.util.Optional;

@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public void register(String username, String password, Role role) {
        try {
            userRepository.registerUser(username, password, role);
        } catch (DuplicateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void login(String username, String password) {
        userRepository.loginUser(username, password);
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(userRepository.getUser());
    }
}