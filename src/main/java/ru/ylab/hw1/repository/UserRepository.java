package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void update(User user);

    void delete(String username);
}
