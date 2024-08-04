package ru.ylab.hw1.datasource.impl;

import lombok.Getter;
import ru.ylab.hw1.datasource.CarRepository;
import ru.ylab.hw1.datasource.RoleValidator;
import ru.ylab.hw1.datasource.UserRepository;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.DataNotFoundException;
import ru.ylab.hw1.exceptions.DuplicateException;
import ru.ylab.hw1.exceptions.PermissionsException;

import java.util.HashMap;
import java.util.Map;

public class CarRepositoryImpl implements CarRepository {
    @Getter
    private final Map<String, Car> cars = new HashMap<>();
    private final RoleValidator roleValidator;
    private final UserRepository userRepository;

    public CarRepositoryImpl(RoleValidator roleValidator, UserRepository userRepository) {
        this.roleValidator = roleValidator;
        this.userRepository = userRepository;
    }

    public void addCar(Car car) throws DuplicateException {

        try {
            roleValidator.checkPermissionNotCustomer(getRole());
        } catch (PermissionsException e) {
            System.out.println(e.getMessage());
        }

        if (cars.containsKey(car.getVinNumber())) {
            throw new DuplicateException("Car with VIN: " + car.getVinNumber() + " has already exist");
        }

        String upperCaseVin = car.getVinNumber().toUpperCase();
        car.setVinNumber(upperCaseVin);
        cars.put(car.getVinNumber(), car);
    }

    public void editCar(Car car) throws DataNotFoundException, PermissionsException {

        roleValidator.checkPermissionNotCustomer(getRole());

        if (!cars.containsKey(car.getVinNumber())) {
            throw new DataNotFoundException("Car with VIN: " + car.getVinNumber() + " not found");
        }

        cars.put(car.getVinNumber(), car);
    }

    public void deleteCar(String vinNumber) throws DataNotFoundException, PermissionsException {

        roleValidator.checkPermissionNotCustomer(getRole());

        if (!cars.containsKey(vinNumber)) {
            throw new DataNotFoundException("Car with VIN: " + vinNumber + " not found");
        }

        cars.remove(vinNumber);
    }

    private Role getRole() {
        return userRepository.getUser().getRole();
    }
}
