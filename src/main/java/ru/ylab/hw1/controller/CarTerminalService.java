package ru.ylab.hw1.controller;

import ru.ylab.hw1.service.AuthService;
import ru.ylab.hw1.service.impl.AuthServiceImpl;
import ru.ylab.hw1.dto.Car;

import java.util.Scanner;

public class CarTerminalService {
    private final AuthService authService = new AuthServiceImpl();

    private final Scanner scanner;

    public CarTerminalService() {
        this.scanner = new Scanner(System.in);
    }

    public int getChoice() {
        System.out.println("1. View all available cars");
        System.out.println("2. Add new car");
        System.out.println("3. Edit information about car");
        System.out.println("4. Delete car");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public Car addCar() {
        Car car;
        System.out.print("Enter car brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine();
        System.out.print("Enter car year: ");
        int year = scanner.nextInt();
        System.out.print("Enter car price: ");
        int price = scanner.nextInt();
        System.out.println("Enter car condition: ");
        String condition = scanner.nextLine();
        System.out.println("Enter car VIN number: ");
        String vin = scanner.nextLine();
        return new Car(brand, model, year, price, condition, vin);
    }

}
