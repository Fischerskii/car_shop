package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

import java.util.Map;

public interface UserService {

    void register(String username, String password, Role role);

    User login(String username, String password);

    Map<String, User> viewUsers();
}
