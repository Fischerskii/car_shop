package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.repository.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCar_ShouldSaveCar() {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carService.addCar(car);

        assertThat(savedCar).isEqualTo(car);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void editCar_ShouldEditCar() {
        Car updatedCar = new Car("Honda", "Accord", 2021, 32000, "New");

        carService.editCar(0, updatedCar);

        verify(carRepository, times(1)).edit(0, updatedCar);
    }

    @Test
    void deleteCar_ShouldDeleteCar() {
        carService.deleteCar(0);

        verify(carRepository, times(1)).delete(0);
    }

    @Test
    void viewCars_ShouldPrintAllCars() {
        Car car1 = new Car("Toyota", "Camry", 2020, 30000, "New");
        Car car2 = new Car("Honda", "Accord", 2021, 32000, "New");
        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        carService.viewCars();

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getAllCars_ShouldReturnAllCars() {
        Car car1 = new Car("Toyota", "Camry", 2020, 30000, "New");
        Car car2 = new Car("Honda", "Accord", 2021, 32000, "New");
        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        List<Car> cars = carService.getAllCars();

        assertThat(cars).containsExactly(car1, car2);
        verify(carRepository, times(1)).findAll();
    }
}