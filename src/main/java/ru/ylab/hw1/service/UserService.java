package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;

public interface UserService {

    void register(String username, String password, User.Role role);

    User login(String username, String password);

    void viewUsers();
}
