package ru.ylab.hw1;

import ru.ylab.hw1.service.AuthService;
import ru.ylab.hw1.service.TerminalService;
import ru.ylab.hw1.service.UserService;

public class CarShopService {
    private static final UserService userService = new UserService();
    private static final TerminalService terminalService = new TerminalService();
    private static final AuthService authService = new AuthService(userService, terminalService);
    public static void main(String[] args) {
        while (true) {
            int choice = terminalService.getChoice();

            switch (choice) {
                case 1:
                    authService.register();
                    break;
                case 2:
                    authService.login();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    terminalService.showMessage("Invalid choice. Please try again.");
            }
        }
    }
}
