package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;

import java.util.List;

@Slf4j
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {
        Car savedCar = carRepository.save(car);
        log.info("Car added: {}", car);
        return savedCar;
    }

    public void editCar(int vinNumber, Car updatedCar) {
        carRepository.edit(vinNumber, updatedCar);
        log.info("Car: {} updated", updatedCar);
    }

    public void deleteCar(int index) {
        carRepository.delete(index);
        log.info("Car: {} deleted", index);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
