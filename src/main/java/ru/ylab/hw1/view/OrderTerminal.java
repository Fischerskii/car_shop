package ru.ylab.hw1.view;

import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.service.impl.CarServiceImpl;
import ru.ylab.hw1.service.impl.OrderServiceImpl;
import ru.ylab.hw1.service.impl.UserServiceImpl;

import java.util.Scanner;

public class OrderTerminal {
    private final OrderService orderService = new OrderServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final CarService carService = new CarServiceImpl();
    private final Logger logger = new Logger();

    protected void createOrder(Scanner scanner) {
        System.out.print("Enter client username: ");
        String username = scanner.nextLine();
        User client = userService.login(username, "");

        if (client != null) {
            carService.viewCars();
            System.out.print("Enter index of car to order: ");
            int carIndex = scanner.nextInt();
            scanner.nextLine();

            Car car = carService.getAllCars().get(carIndex);
            orderService.createOrder(client, car);
            logger.log("Order created for " + car + " by " + client.getUsername());
        } else {
            System.out.println("Client not found.");
        }
    }

    protected void changeOrderStatus(Scanner scanner) {
        orderService.viewOrders();
        System.out.print("Enter order ID to change status: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new status (1. PENDING, 2. COMPLETED, 3. CANCELLED): ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        Order.OrderStatus status = switch (statusChoice) {
            case 1 -> Order.OrderStatus.PENDING;
            case 2 -> Order.OrderStatus.COMPLETED;
            case 3 -> Order.OrderStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Invalid status");
        };

        orderService.changeOrderStatus(id, status);
        logger.log("Order status changed to " + status + " for order ID " + id);
    }

    protected void viewOrders() {
        orderService.viewOrders();
    }
}
