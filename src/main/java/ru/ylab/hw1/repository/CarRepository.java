package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Car;

import java.util.List;

public interface CarRepository {
    Car save(Car car);

    void edit(int index, Car updatedCar);

    void delete(int index);

    List<Car> findAll();
}
