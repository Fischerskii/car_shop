package ru.ylab.hw1.datasource;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.DuplicateException;

public interface UserRepository {

    void registerUser(String userName, String password, Role role) throws DuplicateException;

    void loginUser(String customerName, String password);

    User getUser();
}
