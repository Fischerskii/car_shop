package ru.ylab.hw1.repository.impl;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    public Map<String, User> findAll() {
        return new HashMap<>(users);
    }
}
