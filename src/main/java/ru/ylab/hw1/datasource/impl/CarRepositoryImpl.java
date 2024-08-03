package ru.ylab.hw1.datasource.impl;

import ru.ylab.hw1.datasource.CarRepository;
import ru.ylab.hw1.datasource.RoleValidator;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.DataNotFoundException;
import ru.ylab.hw1.exceptions.DuplicateException;
import ru.ylab.hw1.exceptions.PermissionsException;

import java.util.HashMap;
import java.util.Map;

public class CarRepositoryImpl implements CarRepository {
    private final Map<String, Car> cars = new HashMap<>();
    private final RoleValidator roleValidator;

    public CarRepositoryImpl(Role role) {
        this.roleValidator = new RoleValidator(role);
    }

    public Map<String, Car> getCars() throws PermissionsException {

        roleValidator.checkPermissionNotCustomer();

        return cars;
    }

    public void addCar(Car car) throws DuplicateException, PermissionsException {

        roleValidator.checkPermissionNotCustomer();

        if (cars.containsKey(car.getVinNumber())) {
            throw new DuplicateException("Car with VIN: " + car.getVinNumber() + " has already exist");
        }
        cars.put(car.getVinNumber(), car);
    }

    public void editCar(Car car) throws DataNotFoundException, PermissionsException {

        roleValidator.checkPermissionNotCustomer();

        if (!cars.containsKey(car.getVinNumber())) {
            throw new DataNotFoundException("Car with VIN: " + car.getVinNumber() + " not found");
        }

        cars.put(car.getVinNumber(), car);
    }

    public void deleteCar(Car car) throws DataNotFoundException, PermissionsException {

        roleValidator.checkPermissionNotCustomer();

        if (!cars.containsKey(car.getVinNumber())) {
            throw new DataNotFoundException("Car with VIN: " + car.getVinNumber() + " not found");
        }

        cars.remove(car.getVinNumber());
    }
}
