package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);

    void edit(Car car);

    void delete(String vinNumber);

    List<Car> findAll();

    Optional<Car> findByVin(String vinNumber);
}
