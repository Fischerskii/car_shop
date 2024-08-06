package ru.ylab.hw1.view;

import lombok.Getter;
import lombok.Setter;
import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.dto.User;

import java.util.Scanner;

public class Terminal {
    private final AuthTerminal authTerminal;
    private final CarTerminal carTerminal;
    private final OrderTerminal orderTerminal;
    private final RequestTerminal requestTerminal;

    @Getter
    @Setter
    private User currentUser;
    private final Logger logger = new Logger();

    public Terminal() {
        this.authTerminal = new AuthTerminal(this);
        this.carTerminal = new CarTerminal();
        this.orderTerminal = new OrderTerminal();
        this.requestTerminal = new RequestTerminal();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (currentUser == null) {
                showLoginMenu(scanner);
            } else {
                showMainMenu(scanner);
            }
        }
    }

    private void showLoginMenu(Scanner scanner) {
        System.out.println("-------------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("-------------\n");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                authTerminal.registerUser(scanner);
                break;
            case 2:
                authTerminal.loginUser(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void showMainMenu(Scanner scanner) {
        System.out.println("---------------------------");
        System.out.println("1. Manage Cars");
        System.out.println("2. Manage Orders");
        System.out.println("3. Manage Service Requests");
        System.out.println("4. View Users");
        System.out.println("5. Logout");
        System.out.println("6. View Logs");
        System.out.println("7. Export Logs");
        System.out.println("---------------------------");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                showCarsMenu(scanner);
                break;
            case 2:
                showOrdersMenu(scanner);
                break;
            case 3:
                showRequestMenu(scanner);
                break;
            case 4:
                authTerminal.viewUsers();
                break;
            case 5:
                currentUser = null;
                break;
            case 6:
                logger.viewLogs();
                break;
            case 7:
                exportLogs(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void showCarsMenu(Scanner scanner) {
        System.out.println("1. View Cars");
        System.out.println("2. Add Car");
        System.out.println("3. Edit Car");
        System.out.println("4. Delete Car");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                carTerminal.viewCars();
                break;
            case 2:
                carTerminal.addCar(scanner);
                break;
            case 3:
                carTerminal.editCar(scanner);
                break;
            case 4:
                carTerminal.deleteCar(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void showOrdersMenu(Scanner scanner) {
        System.out.println("1. View Orders");
        System.out.println("2. Create Order");
        System.out.println("3. Change Order Status");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                orderTerminal.viewOrders();
                break;
            case 2:
                orderTerminal.createOrder(scanner);
                break;
            case 3:
                orderTerminal.changeOrderStatus(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void showRequestMenu(Scanner scanner) {
        System.out.println("1. View Service Requests");
        System.out.println("2. Create Service Request");
        System.out.println("3. Change Service Request Status");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                requestTerminal.viewServiceRequests();
                break;
            case 2:
                requestTerminal.createServiceRequest(scanner);
                break;
            case 3:
                requestTerminal.changeServiceRequestStatus(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }



    private void exportLogs(Scanner scanner) {
        System.out.print("Enter filename to export logs: ");
        String filename = scanner.nextLine();
        logger.exportLogs(filename);
        System.out.println("Logs exported to " + filename);
    }
}
