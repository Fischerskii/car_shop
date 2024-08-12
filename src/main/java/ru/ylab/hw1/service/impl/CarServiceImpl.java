package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.repository.CarRepository;
import ru.ylab.hw1.service.CarService;
import ru.ylab.hw1.dto.Car;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CarService} interface that provides
 * methods for managing cars in the car dealership application.
 */
@Slf4j
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    /**
     * @param carRepository the car repository to interact with the data source
     */
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Adds a new car to the repository.
     *
     * @param car the data object representing the car to be added
     */
    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    /**
     * Edits the details of an existing car in the repository.
     *
     * @param car the data object containing the updated car information
     */
    @Override
    public void editCar(Car car) {
        carRepository.edit(car);
    }


    /**
     * Delete a car from the data source based on its VIN number.
     *
     * @param vinNumber the VIN number of the car to be deleted
     */
    @Override
    public void deleteCar(String vinNumber) {
        carRepository.delete(vinNumber);
    }

    /**
     * Retrieves a list of all cars from the repository.
     *
     * @return a list of {@link Car} objects representing all cars
     */
    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Retrieves a car from the repository based on its VIN number.
     *
     * @param vinNumber the VIN number of the car to be retrieved
     * @return an {@link Optional} containing the {@link Car} if found, or an empty Optional if not found
     */
    @Override
    public Optional<Car> getCar(String vinNumber) {
        return carRepository.findByVin(vinNumber);
    }
}
