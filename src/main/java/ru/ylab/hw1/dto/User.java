package ru.ylab.hw1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    public enum Role {
        ADMIN, MANAGER, CLIENT
    }

    private String username;
    private String password;
    private Role role;
}
