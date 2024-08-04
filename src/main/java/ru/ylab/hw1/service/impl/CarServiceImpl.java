package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    private final List<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void editCar(int index, Car updatedCar) {
        cars.set(index, updatedCar);
    }

    public void deleteCar(int index) {
        cars.remove(index);
    }

    public void viewCars() {
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ". " + cars.get(i));
        }
    }

    public List<Car> getAllCars() {
        return cars;
    }
}
