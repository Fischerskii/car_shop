package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.CarDTO;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    CarDTO save(CarDTO carDTO);

    void edit(CarDTO carDTO);

    void delete(String vinNumber);

    List<CarDTO> findAll();

    Optional<CarDTO> findByVin(String vinNumber);
}
