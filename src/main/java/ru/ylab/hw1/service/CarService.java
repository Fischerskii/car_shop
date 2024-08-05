package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;

import java.util.List;

public interface CarService {

    Car addCar(Car car);

    void editCar(int index, Car updatedCar);

    void deleteCar(int index);

    void viewCars();

    List<Car> getAllCars();
}
