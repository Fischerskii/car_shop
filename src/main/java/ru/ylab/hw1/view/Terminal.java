package ru.ylab.hw1.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.config.DatabaseConfig;
import ru.ylab.hw1.dto.LogEntryDTO;
import ru.ylab.hw1.dto.UserDTO;
import ru.ylab.hw1.repository.impl.LoggerRepositoryImpl;
import ru.ylab.hw1.service.LoggerService;
import ru.ylab.hw1.service.impl.LoggerServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Terminal {
    private final AuthTerminal authTerminal;
    private final CarTerminal carTerminal;
    private final OrderTerminal orderTerminal;
    private LoggerService loggerService;

    @Getter
    @Setter
    private UserDTO currentUserDTO;

    public Terminal() {
        TerminalFactory terminalFactory = new DefaultTerminalFactory(this, loggerService);
        this.authTerminal = terminalFactory.createAuthTerminal();
        this.carTerminal = terminalFactory.createCarTerminal();
        this.orderTerminal = terminalFactory.createOrderTerminal();
    }

    {
        try {
            this.loggerService = new LoggerServiceImpl(new LoggerRepositoryImpl(DatabaseConfig.getConnection()));
        } catch (SQLException e) {
            log.error("Can't connect to database", e);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (currentUserDTO == null) {
                authTerminal.showAuthMenu(scanner);
            } else {
                showMainMenu(scanner);
            }
        }
    }

    private void showMainMenu(Scanner scanner) {
        System.out.println("---------------------------");
        System.out.println("1. Manage Cars");
        System.out.println("2. Manage Orders");
        System.out.println("3. View Users");
        System.out.println("4. Logout");
        System.out.println("5. View Logs");
        System.out.println("6. Export Logs");
        System.out.println("---------------------------");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                carTerminal.execute(scanner);
                break;
            case 2:
                orderTerminal.execute(scanner);
                break;
            case 3:
                authTerminal.viewUsers();
                break;
            case 4:
                currentUserDTO = null;
                break;
            case 5:
                viewLogs(scanner);
                break;
            case 6:
                exportLogs(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void viewLogs(Scanner scanner) {
        System.out.print("Enter username to filter by (or leave blank): ");
        String userFilter = scanner.nextLine().trim();
        userFilter = userFilter.isEmpty() ? null : userFilter;

        System.out.print("Enter action type to filter by (or leave blank): ");
//        String actionTypeFilter = scanner.nextLine().trim();
//        ActionType actionType = actionTypeFilter.isEmpty() ? null : ActionType.valueOf(actionTypeFilter.toUpperCase());

        List<LogEntryDTO> logs = loggerService.getLogsByUser(userFilter);
        logs.forEach(System.out::println);
    }

    private void exportLogs(Scanner scanner) {
        System.out.print("Enter filename to export logs: ");
        String filename = scanner.nextLine();

        System.out.print("Enter username to filter by (or leave blank): ");
//        String userFilter = scanner.nextLine().trim();
//        userFilter = userFilter.isEmpty() ? null : userFilter;

        System.out.print("Enter action type to filter by (or leave blank): ");
        String actionTypeFilter = scanner.nextLine().trim();
//        ActionType actionType = actionTypeFilter.isEmpty() ? null : ActionType.valueOf(actionTypeFilter.toUpperCase());

        loggerService.exportLogsToFile(filename);
        System.out.println("Logs exported to " + filename);
    }
}
