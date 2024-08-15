package ru.ylab.hw.view;

import ru.ylab.hw.dto.Order;
import ru.ylab.hw.enums.ActionType;
import ru.ylab.hw.enums.OrderStatus;
import ru.ylab.hw.repository.impl.CarRepositoryImpl;
import ru.ylab.hw.repository.impl.OrderRepositoryImpl;
import ru.ylab.hw.service.CarService;
import ru.ylab.hw.service.LoggerService;
import ru.ylab.hw.service.OrderService;
import ru.ylab.hw.service.impl.CarServiceImpl;
import ru.ylab.hw.service.impl.OrderServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class OrderTerminal implements TerminalAction {
    private final OrderService orderService;
    private final CarService carService;
    private final Terminal terminal;
    private final LoggerService loggerService;

    public OrderTerminal(Terminal terminal, LoggerService loggerService) {
        this.terminal = terminal;
        this.orderService = new OrderServiceImpl(new OrderRepositoryImpl());
        this.carService = new CarServiceImpl(new CarRepositoryImpl());
        this.loggerService = loggerService;
    }

    @Override
    public void execute(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("-------------");
            System.out.println("1. View Orders");
            System.out.println("2. Create Order");
            System.out.println("3. Change Order Status");
            System.out.println("4. Back to Main Menu");
            System.out.println("-------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewOrders();
                case 2 -> createOrder(scanner);
                case 3 -> changeOrderStatus(scanner);
                case 4 -> exit = true;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void createOrder(Scanner scanner) {
        String vinNumber = "";
        boolean validVin = false;

        while (!validVin) {
            System.out.print("Enter car VIN number: ");
            vinNumber = scanner.nextLine();

            if (carService.getCar(vinNumber).isPresent()) {
                validVin = true;
            } else {
                System.out.println("Car with VIN number " + vinNumber + " does not exist. Please try again.");
            }
        }

        String username = terminal.getCurrentUser().getUsername();

        Order newOrder = Order.builder()
                .id(UUID.randomUUID())
                .userName(username)
                .carVinNumber(vinNumber)
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();

        orderService.createOrder(newOrder);
        loggerService.logAction(username, ActionType.CREATE_ORDER, "Created order for VIN: " + vinNumber);
    }

    public void changeOrderStatus(Scanner scanner) {
        viewOrders();

        String orderId = "";
        boolean validOrderId = false;

        while (!validOrderId) {
            System.out.print("Enter order ID: ");
            orderId = scanner.nextLine();

            if (orderService.getOrderById(UUID.fromString(orderId)).isPresent()) {
                validOrderId = true;
            } else {
                System.out.println("Order with ID " + orderId + " does not exist. Please try again.");
            }
        }

        System.out.print("Enter new status (1. PENDING, 2. COMPLETED, 3. CANCELLED): ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();
        OrderStatus status = switch (statusChoice) {
            case 1 -> OrderStatus.PENDING;
            case 2 -> OrderStatus.COMPLETED;
            case 3 -> OrderStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Invalid status");
        };

        orderService.changeOrderStatus(UUID.fromString(orderId), status);
        loggerService.logAction(terminal.getCurrentUser().getUsername(),
                ActionType.CHANGE_ORDER_STATUS,
                "Order ID " + orderId + " status changed to " + status);
    }

    public void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(System.out::println);
    }
}
