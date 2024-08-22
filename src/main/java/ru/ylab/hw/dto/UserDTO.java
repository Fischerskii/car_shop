package ru.ylab.hw.dto;

import lombok.Data;
import ru.ylab.hw.enums.Role;

@Data
public class UserDTO {
    private String username;
    private String password;
    private Role role;

    public UserDTO(String username, String password, Role role) {
        validateUsername(username);
        validatePassword(password);
        validateRole(role);

        this.username = username;
        this.password = password;
        this.role = role;
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
    }
}
