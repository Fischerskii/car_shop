package ru.ylab.hw1.view;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.audit.Logger;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.enums.ActionType;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.repository.impl.CarRepositoryImpl;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.service.impl.CarServiceImpl;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class CarTerminal {
    private final Terminal terminal;
    private final CarRepository carRepository = new CarRepositoryImpl();
    private final CarService carService = new CarServiceImpl(carRepository);
    
    private final Logger logger = new Logger();

    public CarTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void viewCars() {
        List<Car> cars = carService.getAllCars();
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ". " + cars.get(i));
        }
    }

    protected void addCar(Scanner scanner) {
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

        Car car = new Car(brand, model, year, price, condition);
        carService.addCar(car);
        logger.log(terminal.getCurrentUser().getUsername(), ActionType.ADD_CAR, "Car " + car + " added");
    }

    protected void editCar(Scanner scanner) {
        viewCars();
        System.out.print("Enter index of car to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine();

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

        Car updatedCar = new Car(brand, model, year, price, condition);
        carService.editCar(index, updatedCar);
        logger.log(terminal.getCurrentUser().getUsername(), ActionType.EDIT_CAR, "Car " + updatedCar + " updated");
    }

    protected void deleteCar(Scanner scanner) {
        viewCars();
        System.out.print("Enter index of car to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        carService.deleteCar(index);
        logger.log(terminal.getCurrentUser().getUsername(), ActionType.DELETE_CAR, "Car " + index + " deleted");
    }
}
