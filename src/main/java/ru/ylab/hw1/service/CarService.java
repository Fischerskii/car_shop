package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    void addCar(Car car);

    void editCar(Car car);

    void deleteCar(String vinNumber);

    List<Car> getAllCars();

    Optional<Car> getCar(String vinNumber);
}
