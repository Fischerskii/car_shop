package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.service.CarService;

import static org.assertj.core.api.Assertions.assertThat;

class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carService = new CarServiceImpl();
    }

    @Test
    void testAddCar() {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        carService.addCar(car);
        assertThat(carService.getAllCars()).hasSize(1);
    }

    @Test
    void testEditCar() {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        carService.addCar(car);
        Car updatedCar = new Car("Honda", "Accord", 2021, 28000, "New");
        carService.editCar(0, updatedCar);
        assertThat(carService.getAllCars().get(0).getBrand()).isEqualTo("Honda");
    }

    @Test
    void testDeleteCar() {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        carService.addCar(car);
        carService.deleteCar(0);
        assertThat(carService.getAllCars()).isEmpty();
    }
}