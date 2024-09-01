package ru.ylab.carshopapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ylab.auditlogging.annotation.Audit;
import ru.ylab.carshopapp.repository.CarRepository;
import ru.ylab.carshopapp.service.CarService;
import ru.ylab.carshopapp.entity.Car;

import java.util.List;
import java.util.Optional;

import static ru.ylab.common.enums.ActionType.CAR_ACTIONS;

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
