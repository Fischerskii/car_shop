package ru.ylab.hw1.view;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.repository.UserRepository;
import ru.ylab.hw1.repository.impl.UserRepositoryImpl;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.service.impl.UserServiceImpl;

import java.util.Scanner;

@Slf4j
public class AuthTerminal {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserService userService = new UserServiceImpl(userRepository);
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
        Role role = switch (roleChoice) {
            case 1 -> Role.ADMIN;
            case 2 -> Role.MANAGER;
            case 3 -> Role.CLIENT;
            default -> throw new IllegalArgumentException("Invalid role");
        };

        userService.register(username, password, role);
        logger.log(username, ActionType.REGISTER, "User " + username + " registered: ");
    }

    protected void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        terminal.setCurrentUser(userService.login(username, password));

        if (terminal.getCurrentUser() != null) {
            logger.log(username, ActionType.LOGIN, "User logged in: " + username);
        } else {
            System.out.println("Invalid credentials, please try again.");
        }
    }

    protected void viewUsers() {
        userService.viewUsers().values().forEach(System.out::println);
    }
}
