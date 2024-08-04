package ru.ylab.hw1.datasource;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.exceptions.DataNotFoundException;
import ru.ylab.hw1.exceptions.DuplicateException;
import ru.ylab.hw1.exceptions.PermissionsException;

import java.util.Map;

public interface CarRepository {

    Map<String, Car> getCars();

    void addCar(Car car) throws DuplicateException, PermissionsException;

    void editCar(Car car) throws DataNotFoundException, PermissionsException;

    void deleteCar(String vinNumber) throws DataNotFoundException, PermissionsException;
}
