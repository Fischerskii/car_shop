package ru.ylab.hw1.datasource;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

public interface UserRepository {

    boolean registerUser(String userName, String password, Role role);

    User loginUser(String customerName, String password);

    User getUser();
}
