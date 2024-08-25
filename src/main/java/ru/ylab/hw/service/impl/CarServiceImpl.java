package ru.ylab.hw.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ylab.hw.audit.Audit;
import ru.ylab.hw.dto.CarDTO;
import ru.ylab.hw.mapper.CarMapper;
import ru.ylab.hw.repository.CarRepository;
import ru.ylab.hw.service.CarService;
import ru.ylab.hw.entity.Car;

import java.util.List;
import java.util.Optional;

import static ru.ylab.hw.enums.ActionType.*;

@Service
@Slf4j
@Audit(actionType = CAR_ACTIONS)
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
