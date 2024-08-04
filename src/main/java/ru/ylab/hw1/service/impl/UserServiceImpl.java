package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.dto.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final Map<String, User> users = new HashMap<>();

    public void register(String username, String password, User.Role role) {
        users.put(username, new User(username, password, role));
    }

    public User login(String username, String password) {
        User user = users.get(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public void viewUsers() {
        users.values().forEach(System.out::println);
    }
}
