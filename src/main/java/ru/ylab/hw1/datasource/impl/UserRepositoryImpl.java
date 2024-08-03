package ru.ylab.hw1.datasource.impl;

import lombok.Getter;
import ru.ylab.hw1.datasource.UserRepository;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();
    @Getter
    private User user;

    @Override
    public boolean registerUser(String userName, String password, Role role) {
        if (users.containsKey(userName)) {
            return false;
        }

        users.put(userName, new User(userName, password, role));
        return true;
    }

    @Override
    public User loginUser(String customerName, String password) {
        user = users.get(customerName);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}
