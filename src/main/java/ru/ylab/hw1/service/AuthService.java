package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;

import java.util.Optional;

public interface AuthService {
    void register();

    void login();

    void viewCurrentUser();

    Optional<User> getCurrentUser();
}
