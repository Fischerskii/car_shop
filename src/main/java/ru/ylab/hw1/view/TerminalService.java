package ru.ylab.hw1.view;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TerminalService {
    private final AuthTerminalService authTerminalService;
    private final CarTerminalService carTerminalService;

    public TerminalService(AuthTerminalService authTerminalService, CarTerminalService carTerminalService) {
        this.authTerminalService = authTerminalService;
        this.carTerminalService = carTerminalService;
    }

    public void authenticatePanel() {
        boolean toggle = true;
        while (toggle) {
            int choice = authTerminalService.getChoice();

            switch (choice) {
                case 1:
                    authTerminalService.registerUser();
                    break;
                case 2:
                    authTerminalService.loginUser();
                    carActionPanel();
                    toggle = false;
                    break;
                case 3:
                    authTerminalService.currentAuthorizedUser();
                    break;
                default:
                    showMessage();
            }
        }
    }

    public void carActionPanel() {

        int choice = 0;
        while (choice != 5) {
            choice = carTerminalService.getChoice();

            switch (choice) {
                case 1:
                    carTerminalService.addCar();
                    break;
                case 2:
                    carTerminalService.getCars();
                    break;
                case 3:
                    carTerminalService.editCar();
                    break;
                case 4:
                    carTerminalService.deleteCar();
                    break;
                default:
                    showMessage();
            }
        }
    }

    public void showMessage() {
        System.out.println("Invalid choice. Please try again.");
    }


}
