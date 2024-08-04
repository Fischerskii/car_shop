package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

import java.util.Optional;

public interface AuthService {
    void register(String username, String password, Role role);

    void login(String username, String password);

    Optional<User> getCurrentUser();
}
