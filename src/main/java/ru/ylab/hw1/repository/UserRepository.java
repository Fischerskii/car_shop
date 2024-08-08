package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.User;

import java.util.Map;

public interface UserRepository {

    void save(User user);

    Map<String, User> findAll();
}
