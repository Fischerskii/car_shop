package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    Map<String, User> users = new HashMap<>();


    public boolean registerUser(String userName, String password, Role role) {
        if (users.containsKey(userName)) {
            return false;
        }

        users.put(userName, new User(userName, password, role));
        return true;
    }

    public User loginUser(String customerName, String password) {
        User user = users.get(customerName);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}
