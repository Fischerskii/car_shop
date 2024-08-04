package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.exceptions.DataNotFoundException;

import java.util.Map;

public interface CarService {

    void addCar(Car car);

    Car getCar(String vinNumber) throws DataNotFoundException;

    Map<String, Car> getCars();

    void deleteCar(String vinNumber);
}
