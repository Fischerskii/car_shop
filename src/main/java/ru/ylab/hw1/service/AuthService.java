package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

public class AuthService {
    private final UserService userService;
    private final TerminalService terminalService;

    public AuthService(UserService userService, TerminalService terminalService) {
        this.userService = userService;
        this.terminalService = terminalService;
    }

    public void register() {
        String username = terminalService.getUsername();
        String password = terminalService.getPassword();
        Role role = terminalService.getRole();

        if (userService.registerUser(username, password, role)) {
            terminalService.showMessage("User registered successfully");
        } else {
            terminalService.showMessage("Username already exists. Please enter another username");
        }
    }

    public void login() {
        String username = terminalService.getUsername();
        String password = terminalService.getPassword();

        User user = userService.loginUser(username, password);
        if (user != null) {
            terminalService.showUserRole(user);
        } else {
            terminalService.showMessage("Invalid username or password. Please try again");
        }
    }
}