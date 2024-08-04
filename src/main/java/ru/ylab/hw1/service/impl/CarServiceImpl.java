package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.datasource.CarRepository;
import ru.ylab.hw1.datasource.RoleValidator;
import ru.ylab.hw1.datasource.UserRepository;
import ru.ylab.hw1.datasource.impl.CarRepositoryImpl;
import ru.ylab.hw1.datasource.impl.UserRepositoryImpl;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.exceptions.DataNotFoundException;
import ru.ylab.hw1.exceptions.DuplicateException;
import ru.ylab.hw1.exceptions.PermissionsException;
import ru.ylab.hw1.service.CarService;

import java.util.Map;

@Slf4j
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl() {
        UserRepository userRepository = new UserRepositoryImpl();
        RoleValidator roleValidator = new RoleValidator();
        this.carRepository = new CarRepositoryImpl(roleValidator, userRepository);
    }

    @Override
    public void addCar(Car car) {
        try {
            carRepository.addCar(car);
        } catch (PermissionsException | DuplicateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Car getCar(String vinNumber) throws DataNotFoundException {
        Car car;
        if (!carRepository.getCars().containsKey(vinNumber)) {
            throw new DataNotFoundException("Car with VIN: " + vinNumber + " not found. Please enter VIN again.");
        }
        car = carRepository.getCars().get(vinNumber);
        return car;
    }

    @Override
    public Map<String, Car> getCars() {
        if (carRepository.getCars().entrySet().isEmpty()) {
            System.out.println("Cars not found.");
        } else {
            System.out.println("List of all available cars: ");
        }
        return carRepository.getCars();
    }

    @Override
    public void deleteCar(String vinNumber) {
        try {
            carRepository.deleteCar(vinNumber);
        } catch (DataNotFoundException | PermissionsException e) {
            System.out.println(e.getMessage());
        }
        log.info("Car with VIN: {} has been successfully removed.", vinNumber);
    }

}
