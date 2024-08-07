package ru.ylab.hw1.service.impl;

import lombok.AllArgsConstructor;
import ru.ylab.hw1.repository.impl.CarRepositoryImpl;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;

import java.util.List;

@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepositoryImpl carRepository;

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public void editCar(int vinNumber, Car updatedCar) {
        carRepository.edit(vinNumber, updatedCar);
    }

    public void deleteCar(int index) {
        carRepository.delete(index);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
