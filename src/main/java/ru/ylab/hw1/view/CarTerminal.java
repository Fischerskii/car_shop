package ru.ylab.hw1.view;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.dto.CarDTO;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.repository.impl.CarRepositoryImpl;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.service.LoggerService;
import ru.ylab.hw1.service.impl.CarServiceImpl;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class CarTerminal implements TerminalAction {
    private final Terminal terminal;
    private final CarService carService;
    private final LoggerService loggerService;

    public CarTerminal(Terminal terminal, LoggerService loggerService) {
        this.terminal = terminal;
        this.carService = new CarServiceImpl(new CarRepositoryImpl());
        this.loggerService = loggerService;
    }

    @Override
    public void execute(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("-------------");
            System.out.println("1. View Cars");
            System.out.println("2. Add Car");
            System.out.println("3. Edit Car");
            System.out.println("4. Delete Car");
            System.out.println("5. Back to Main Menu");
            System.out.println("-------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewCars();
                case 2 -> addCar(scanner);
                case 3 -> editCar(scanner);
                case 4 -> deleteCar(scanner);
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    protected void viewCars() {
        List<CarDTO> carDTOS = carService.getAllCars();
        for (int i = 0; i < carDTOS.size(); i++) {
            System.out.println(i + ". " + carDTOS.get(i));
        }
    }

    protected void addCar(Scanner scanner) {
        System.out.println("Enter VIN number: ");
        String vin = scanner.nextLine();
        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter condition: ");
        String condition = scanner.nextLine();

        CarDTO carDTO = new CarDTO(vin, brand, model, year, price, condition);
        carService.addCar(carDTO);
        loggerService.logAction(terminal.getCurrentUserDTO().getUsername(), ActionType.ADD_CAR, "Car " + carDTO + " added.");
    }

    protected void editCar(Scanner scanner) {
        System.out.print("Enter VIN of car to edit: ");
        String vin = scanner.nextLine();
        System.out.print("Enter new brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter new model: ");
        String model = scanner.nextLine();
        System.out.print("Enter new year: ");
        int year = scanner.nextInt();
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter new condition: ");
        String condition = scanner.nextLine();

        CarDTO updatedCarDTO = new CarDTO(vin, brand, model, year, price, condition);
        carService.editCar(updatedCarDTO);
        loggerService.logAction(terminal.getCurrentUserDTO().getUsername(), ActionType.EDIT_CAR, "Car " + updatedCarDTO + " updated.");
    }

    protected void deleteCar(Scanner scanner) {
        System.out.print("Enter VIN of car to delete: ");
        String vin = scanner.nextLine();

        carService.deleteCar(vin);
        loggerService.logAction(terminal.getCurrentUserDTO().getUsername(), ActionType.DELETE_CAR, "Car with VIN: " + vin + " was deleted.");
    }
}
