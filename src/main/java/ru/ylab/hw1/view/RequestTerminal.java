package ru.ylab.hw1.view;

import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.service.RequestService;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.service.impl.CarServiceImpl;
import ru.ylab.hw1.service.impl.RequestServiceImpl;
import ru.ylab.hw1.service.impl.UserServiceImpl;

import java.util.Scanner;

public class RequestTerminal {
    UserService userService = new UserServiceImpl();
    CarService carService = new CarServiceImpl();
    RequestService requestService = new RequestServiceImpl();
    
    private final Logger logger = new Logger();

    protected void createServiceRequest(Scanner scanner) {
        System.out.print("Enter client username: ");
        String username = scanner.nextLine();
        User client = userService.login(username, "");

        if (client != null) {
            carService.viewCars();
            System.out.print("Enter index of car for service request: ");
            int carIndex = scanner.nextInt();
            scanner.nextLine();

            Car car = carService.getAllCars().get(carIndex);
            System.out.print("Enter service description: ");
            String description = scanner.nextLine();

            requestService.createServiceRequest(client, car, description);
            logger.log("Service request created for " + car + " by " + client.getUsername() + " [" + description + "]");
        } else {
            System.out.println("Client not found.");
        }
    }

    protected void changeServiceRequestStatus(Scanner scanner) {
        requestService.viewServiceRequests();
        System.out.print("Enter service request ID to change status: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new status (1. PENDING, 2. COMPLETED, 3. CANCELLED): ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        Request.ServiceStatus status = switch (statusChoice) {
            case 1 -> Request.ServiceStatus.PENDING;
            case 2 -> Request.ServiceStatus.COMPLETED;
            case 3 -> Request.ServiceStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Invalid status");
        };

        requestService.changeServiceRequestStatus(id, status);
        logger.log("Service request status changed to " + status + " for request ID " + id);
    }

    protected void viewServiceRequests() {
        requestService.viewServiceRequests();
    }
}
