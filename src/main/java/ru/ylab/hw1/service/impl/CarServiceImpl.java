package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;

import java.util.List;
import java.util.Optional;


@Slf4j
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void editCar(Car car) {
        carRepository.edit(car);
    }

    @Override
    public void deleteCar(String vinNumber) {
        carRepository.delete(vinNumber);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCar(String vinNumber) {
        return carRepository.findByVin(vinNumber);
    }
}
