package ru.ylab.hw.view;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw.dto.User;
import ru.ylab.hw.enums.ActionType;
import ru.ylab.hw.enums.Role;
import ru.ylab.hw.repository.impl.UserRepositoryImpl;
import ru.ylab.hw.service.LoggerService;
import ru.ylab.hw.service.UserService;
import ru.ylab.hw.service.impl.UserServiceImpl;

import java.util.Scanner;

@Slf4j
public class AuthTerminal implements TerminalAction{
    private final UserService userService;
    private final Terminal terminal;
    private final LoggerService loggerService;

    public AuthTerminal(Terminal terminal, LoggerService loggerService) {
        this.terminal = terminal;
        this.userService = new UserServiceImpl(new UserRepositoryImpl());
        this.loggerService = loggerService;
    }

    @Override
    public void execute(Scanner scanner) {
        showAuthMenu(scanner);
    }

    protected void showAuthMenu(Scanner scanner) {
        System.out.println("-------------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("-------------");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> registerUser(scanner);
            case 2 -> loginUser(scanner);
            default -> System.out.println("Invalid choice, please try again.");
        }
    }

    protected void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        Role role = null;
        while (role == null) {
            System.out.print("Enter role (1. ADMIN, 2. MANAGER, 3. CLIENT): ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            role = switch (roleChoice) {
                case 1 -> Role.ADMIN;
                case 2 -> Role.MANAGER;
                case 3 -> Role.CLIENT;
                default -> {
                    System.out.println("Invalid role, please try again.");
                    yield null;
                }
            };
        }

        userService.register(username, password, role);
        loggerService.logAction(username, ActionType.REGISTER, "User " + username + " registered.");
    }

    protected void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            terminal.setCurrentUser(user);
            loggerService.logAction(username, ActionType.LOGIN, "User " + username + " logged in.");
        } else {
            System.out.println("Invalid credentials, please try again.");
        }
    }

    protected void viewUsers() {
        userService.viewUsers().forEach(System.out::println);
    }
}
