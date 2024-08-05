package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    public Map<String, User> findAll() {
        return users;
    }
}
