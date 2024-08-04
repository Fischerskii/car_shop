package ru.ylab.hw1.view;

import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.service.impl.UserServiceImpl;
import ru.ylab.hw1.dto.User;

import java.util.Scanner;

public class AuthTerminal {
    private final UserService userService = new UserServiceImpl();
    private final Terminal terminal;
    private final Logger logger = new Logger();

    public AuthTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (1. ADMIN, 2. MANAGER, 3. CLIENT): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
        User.Role role = switch (roleChoice) {
            case 1 -> User.Role.ADMIN;
            case 2 -> User.Role.MANAGER;
            case 3 -> User.Role.CLIENT;
            default -> throw new IllegalArgumentException("Invalid role");
        };

        userService.register(username, password, role);
        logger.log("User registered: " + username + " [" + role + "]");
    }

    protected void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        terminal.setCurrentUser(userService.login(username, password));

        if (terminal.getCurrentUser() != null) {
            logger.log("User logged in: " + username);
        } else {
            System.out.println("Invalid credentials, please try again.");
        }
    }

    protected void viewUsers() {
        userService.viewUsers();
    }
}
