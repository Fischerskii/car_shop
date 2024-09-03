package ru.ylab.carshopapp.dto;

import lombok.Builder;
import lombok.Getter;
import ru.ylab.carshopapp.enums.Role;

@Getter
@Builder
public class UserDTO {
    private String username;
    private String password;
    private Role role;

    public void setUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void setRole(Role role) {
        validateRole(role);
        this.role = role;
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 3) {
            throw new IllegalArgumentException("Password must be at least 3 characters long");
        }
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
    }
}