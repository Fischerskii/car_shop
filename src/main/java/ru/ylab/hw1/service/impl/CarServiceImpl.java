package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;

import java.util.List;

public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public void editCar(int vinNumber, Car updatedCar) {
        carRepository.edit(vinNumber, updatedCar);
    }

    public void deleteCar(int index) {
        carRepository.delete(index);
    }

    public void viewCars() {
        List<Car> cars = carRepository.findAll();
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + ". " + cars.get(i));
        }
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
