package ru.ylab.hw1.controller;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.service.AuthService;

@Slf4j
public class TerminalService {
    private final AuthTerminalService authTerminalService;
    private final CarTerminalService carTerminalService;
    private final AuthService authService;

    public TerminalService(AuthTerminalService authTerminalService, CarTerminalService carTerminalService, AuthService authService) {
        this.authTerminalService = authTerminalService;
        this.carTerminalService = carTerminalService;
        this.authService = authService;
    }

    public void authenticate() {

        while (true) {
            int choice = authTerminalService.getChoice();

            switch (choice) {
                case 1:
                    authService.register();
                    break;
                case 2:
                    authService.login();

                    break;
                case 3:
                    authService.viewCurrentUser();
                    break;
                default:
                    authTerminalService.showMessage("Invalid choice. Please try again.");
            }
        }
    }

    public void viewCarsList() {

        while (true) {
            int choice = carTerminalService.getChoice();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }


}
