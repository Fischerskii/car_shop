package ru.ylab.hw1.datasource.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.datasource.UserRepository;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.DuplicateException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();
    @Getter
    private User user;

    @Override
    public void registerUser(String username, String password, Role role) throws DuplicateException{
        if (users.containsKey(username)) {
            throw new DuplicateException("User with username: " + username + " has already registered");
        }

        users.put(username, new User(username, password, role));
        System.out.println("User " + username + " successfully registered");
    }

    @Override
    public void loginUser(String customerName, String password) {
        if (users.containsKey(customerName) && users.get(customerName).getPassword().equals(password)) {
            user = users.get(customerName);
        } else {
            System.out.println("Username " + customerName + " or password is incorrect");
        }
    }
}
