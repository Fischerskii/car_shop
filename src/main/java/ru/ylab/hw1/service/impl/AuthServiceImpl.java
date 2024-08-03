package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.controller.AuthTerminalService;
import ru.ylab.hw1.datasource.UserRepository;
import ru.ylab.hw1.datasource.impl.UserRepositoryImpl;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.service.AuthService;

import java.util.Optional;

@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private AuthTerminalService authTerminalService;

    public AuthServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
        this.authTerminalService = new AuthTerminalService();
    }

    @Override
    public void register() {
        String username = authTerminalService.getUsername();
        String password = authTerminalService.getPassword();
        Role role = authTerminalService.getRole();

        if (userRepository.registerUser(username, password, role)) {
            authTerminalService.showMessage("User registered successfully");
        } else {
            authTerminalService.showMessage("Username already exists. Please enter another username");
        }
    }

    @Override
    public void login() {
        String username = authTerminalService.getUsername();
        String password = authTerminalService.getPassword();

        User user = userRepository.loginUser(username, password);
        if (user != null) {
            authTerminalService.showUserRole(user);
        } else {
            authTerminalService.showMessage("Invalid username or password. Please try again");
        }
    }

    @Override
    public void viewCurrentUser() {
        if (userRepository.getUser() == null) {
            System.out.println("No authorization under any user");
        } else {
            System.out.println("Current session authorized in as user: " + userRepository.getUser().getUsername());
        }
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(userRepository.getUser());
    }
}