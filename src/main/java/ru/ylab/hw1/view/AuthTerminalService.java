package ru.ylab.hw1.view;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.service.AuthService;
import ru.ylab.hw1.service.impl.AuthServiceImpl;

import java.util.Scanner;

public class AuthTerminalService {
    private final AuthService authService = new AuthServiceImpl();
    private final Scanner scanner;

    public AuthTerminalService() {
        this.scanner = new Scanner(System.in);
    }

    public int getChoice() {
        System.out.println("---------------------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. View current user");
        System.out.println("---------------------");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void registerUser() {
        String username = getUsername();
        String password = getPassword();
        Role role = getRole();

        authService.register(username, password, role);
    }

    public void loginUser() {
        String username = getUsername();
        String password = getPassword();

        authService.login(username, password);
    }

    public void currentAuthorizedUser() {
        if (authService.getCurrentUser().isEmpty()) {
            System.out.println("Not authenticated");
        } else {
            System.out.println(authService.getCurrentUser().get().getUsername());
        }
    }

    private String getUsername() {
        System.out.print("Enter user name: ");
        return scanner.nextLine();
    }

    private String getPassword() {
        System.out.print("Enter password: ");
        return scanner.nextLine();
    }

    private Role getRole() {
        System.out.print("Enter role (ADMIN, MANAGER, CUSTOMER): ");
        String roleInput = scanner.nextLine().toUpperCase();
        return Role.valueOf(roleInput);
    }

    private void showUserRole(User user) {
        System.out.println("Welcome " + user.getUsername() + "! Your role is: " + user.getRole() + ".");
    }
}