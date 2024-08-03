package ru.ylab.hw1.controller;

import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.Role;

import java.util.Scanner;

public class AuthTerminalService {
    private final Scanner scanner;

    public AuthTerminalService() {
        this.scanner = new Scanner(System.in);
    }

    public int getChoice() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. View current user");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public String getUsername() {
        System.out.print("Enter user name: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.print("Enter password: ");
        return scanner.nextLine();
    }

    public Role getRole() {
        System.out.print("Enter role (ADMIN, MANAGER, CUSTOMER): ");
        String roleInput = scanner.nextLine().toUpperCase();
        return Role.valueOf(roleInput);
    }

    public void showUserRole(User user) {
        System.out.println("Welcome " + user.getUsername() + "! Your role is: " + user.getRole() + ".");
    }

    //todo Вынести в TerminalService
    public void showMessage(String message) {
        System.out.println(message);
    }
}
