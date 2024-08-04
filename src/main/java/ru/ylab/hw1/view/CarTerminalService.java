package ru.ylab.hw1.view;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.exceptions.DataNotFoundException;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.service.impl.CarServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class CarTerminalService {
    private final CarService carService = new CarServiceImpl();

    private final Scanner scanner;

    public CarTerminalService() {
        this.scanner = new Scanner(System.in);
    }

    public int getChoice() {
        System.out.println("------------------------------");
        System.out.println("1. Add new car");
        System.out.println("2. View all available cars");
        System.out.println("3. Edit information about car");
        System.out.println("4. Delete car");
        System.out.println("5. Logout");
        System.out.println("-------------------------------");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        getIntInput();
        return choice;
    }

    public Car addCar() {
        System.out.print("Enter car brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine();
        System.out.print("Enter car year: ");
        int year = getIntInput();
        System.out.print("Enter car price: ");
        int price = getIntInput();
        System.out.print("Enter car condition: ");
        String condition = scanner.nextLine();
        System.out.print("Enter car VIN number: ");
        String vin = scanner.nextLine();
        Car car = new Car(brand, model, year, price, condition, vin);
        carService.addCar(car);
        log.info("Added car: {}", car);
        return car;
    }

    public void editCar() {
        System.out.print("Enter VIN number for edit car: ");
        String vin = scanner.nextLine();
        try {
            if (carService.getCar(vin) != null) {
                addCar().setVinNumber(vin);
            }
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getCars() {
        carService.getCars().forEach((key, value) -> System.out.println(value));
    }

    public void deleteCar() {
        System.out.print("Enter VIN number for delete car: ");
        String vin = scanner.nextLine();
        carService.deleteCar(vin);
    }

    private int getIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.next();
            }
        }
    }
}
