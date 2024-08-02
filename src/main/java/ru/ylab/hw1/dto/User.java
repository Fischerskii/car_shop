package ru.ylab.hw1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ylab.hw1.enums.Role;

@Getter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Role role;
}
