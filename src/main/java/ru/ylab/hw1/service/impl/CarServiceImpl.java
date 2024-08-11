package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.CarDTO;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void addCar(CarDTO carDTO) {
        carRepository.save(carDTO);
    }

    @Override
    public void editCar(CarDTO carDTO) {
        carRepository.edit(carDTO);
    }

    @Override
    public void deleteCar(String vinNumber) {
        carRepository.delete(vinNumber);
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<CarDTO> getCar(String vinNumber) {
        return carRepository.findByVin(vinNumber);
    }
}
