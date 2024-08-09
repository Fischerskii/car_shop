package ru.ylab.hw1.view;

import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.repository.OrderRepository;
import ru.ylab.hw1.repository.UserRepository;
import ru.ylab.hw1.repository.impl.CarRepositoryImpl;
import ru.ylab.hw1.repository.impl.OrderRepositoryImpl;
import ru.ylab.hw1.repository.impl.UserRepositoryImpl;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.service.impl.CarServiceImpl;
import ru.ylab.hw1.service.impl.OrderServiceImpl;
import ru.ylab.hw1.service.impl.UserServiceImpl;

import java.util.Scanner;

public class OrderTerminal {
    private Terminal terminal;
    private final CarRepository carRepository = new CarRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    private final OrderService orderService = new OrderServiceImpl(orderRepository);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final CarService carService = new CarServiceImpl(carRepository);
    private final CarTerminal carTerminal = new CarTerminal(terminal);
    private final Logger logger = new Logger();

    public OrderTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void createOrder(Scanner scanner) {
        System.out.print("Enter client username: ");
        String username = scanner.nextLine();
        User client = userService.login(username, "");

        if (client != null) {
            carTerminal.viewCars();
            System.out.print("Enter index of car to order: ");
            int carIndex = scanner.nextInt();
            scanner.nextLine();

            Car car = carService.getAllCars().get(carIndex);
            orderService.createOrder(client, car);
            logger.log(terminal.getCurrentUser().getUsername(), ActionType.CREATE_ORDER,
                    "Order from client " + username + " with car " + car + " has been created");
        } else {
            System.out.println("Client not found.");
        }
    }

    protected void changeOrderStatus(Scanner scanner) {
        viewOrders();
        System.out.print("Enter order ID to change status: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new status (1. PENDING, 2. COMPLETED, 3. CANCELLED): ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        OrderStatus status = switch (statusChoice) {
            case 1 -> OrderStatus.PENDING;
            case 2 -> OrderStatus.COMPLETED;
            case 3 -> OrderStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Invalid status");
        };

        orderService.changeOrderStatus(id, status);
        String currentUser = terminal.getCurrentUser().getUsername();
        logger.log(currentUser, ActionType.CHANGE_ORDER_STATUS,
                "Order from client " + currentUser + " with id " + id + " has been changed");
    }

    protected void viewOrders() {
        orderService.getAllOrders();
    }
}
