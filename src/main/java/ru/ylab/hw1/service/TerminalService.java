package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

import java.util.Scanner;

public class TerminalService {
    private final Scanner scanner;

    public TerminalService() {
        this.scanner = new Scanner(System.in);
    }

    public int getChoice() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public String getUserName() {
        System.out.println("Enter user name: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.println("Enter password: ");
        return scanner.nextLine();
    }

    public Role getRole() {
        System.out.println("Enter role (ADMIN, MANAGER, CUSTOMER): ");
        String roleInput = scanner.nextLine().toUpperCase();
        return Role.valueOf(roleInput);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showUserRole(User user) {
        System.out.println("Welcome " + user.getUsername() + "! Your role is: " + user.getRole() + ".");
    }
}
