package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.CarDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {

    void addCar(CarDTO carDTO);

    void editCar(CarDTO carDTO);

    void deleteCar(String vinNumber);

    List<CarDTO> getAllCars();

    Optional<CarDTO> getCar(String vinNumber);
}
