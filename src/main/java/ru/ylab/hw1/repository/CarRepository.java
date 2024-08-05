package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Car;

import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private final List<Car> cars = new ArrayList<>();

    public Car save(Car car) {
        cars.add(car);
        return car;
    }

    public void edit(int index, Car updatedCar) {
        cars.set(index, updatedCar);
    }

    public void delete(int index) {
        cars.remove(index);
    }

    public List<Car> findAll() {
        return cars;
    }
}
