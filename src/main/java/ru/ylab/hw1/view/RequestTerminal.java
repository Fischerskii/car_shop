package ru.ylab.hw1.view;

import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.enums.ServiceStatus;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.repository.RequestRepository;
import ru.ylab.hw1.repository.UserRepository;
import ru.ylab.hw1.repository.impl.CarRepositoryImpl;
import ru.ylab.hw1.repository.impl.RequestRepositoryImpl;
import ru.ylab.hw1.repository.impl.UserRepositoryImpl;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.service.RequestService;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.service.impl.CarServiceImpl;
import ru.ylab.hw1.service.impl.RequestServiceImpl;
import ru.ylab.hw1.service.impl.UserServiceImpl;

import java.util.Scanner;

public class RequestTerminal {
    private Terminal terminal;
    private final CarRepository carRepository = new CarRepositoryImpl();
    private final RequestRepository requestRepository = new RequestRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    private final UserService userService = new UserServiceImpl(userRepository);
    private final CarService carService = new CarServiceImpl(carRepository);
    private final RequestService requestService = new RequestServiceImpl(requestRepository);
    private final CarTerminal carTerminal = new CarTerminal(terminal);

    private final Logger logger = new Logger();

    public RequestTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void createServiceRequest(Scanner scanner) {
        System.out.print("Enter client username: ");
        String username = scanner.nextLine();
        User client = userService.login(username, "");

        if (client != null) {
            carTerminal.viewCars();
            System.out.print("Enter index of car for service request: ");
            int carIndex = scanner.nextInt();
            scanner.nextLine();

            Car car = carService.getAllCars().get(carIndex);
            System.out.print("Enter service description: ");
            String description = scanner.nextLine();

            requestService.createServiceRequest(client, car, description);
            logger.log(username, ActionType.CREATE_SERVICE_REQUEST,
                    "Request created for client " + client + ", car " + car + " has been created");
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
        ServiceStatus status = switch (statusChoice) {
            case 1 -> ServiceStatus.PENDING;
            case 2 -> ServiceStatus.COMPLETED;
            case 3 -> ServiceStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Invalid status");
        };

        requestService.changeServiceRequestStatus(id, status);
        logger.log(terminal.getCurrentUser().getUsername(), ActionType.CHANGE_SERVICE_REQUEST_STATUS,
                "Request with id " + id + " has been changed to status " + status);
    }

    protected void viewServiceRequests() {
        requestService.viewServiceRequests();
    }
}
