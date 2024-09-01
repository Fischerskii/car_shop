package ru.ylab.carshopapp.service;

import ru.ylab.carshopapp.entity.Car;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CarService} interface that provides
 * methods for managing cars in the car dealership application.
 */
public interface CarService {

    /**
     * Adds a new car to the repository.
     *
     * @param car the data object representing the car to be added
     */
    void addCar(Car car);

    /**
     * Edits the details of an existing car in the repository.
     *
     * @param car the data object containing the updated car information
     */
    void editCar(Car car);

    /**
     * Delete a car from the data source based on its VIN number.
     *
     * @param vinNumber the VIN number of the car to be deleted
     */
    void deleteCar(String vinNumber);

    /**
     * Retrieves a list of all cars from the repository.
     *
     * @return a list of {@link Car} objects representing all cars
     */
    List<Car> getAllCars();

    /**
     * Retrieves a car from the repository based on its VIN number.
     *
     * @param vinNumber the VIN number of the car to be retrieved
     * @return an {@link Optional} containing the {@link Car} if found, or an empty Optional if not found
     */
    Optional<Car> getCar(String vinNumber);
}
