package ru.ylab.hw.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw.entity.Car;
import ru.ylab.hw.repository.CarRepository;
import ru.ylab.hw.service.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should add a new car successfully")
    void addCar_ShouldCallSaveMethod() {
        Car car = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");

        carService.addCar(car);

        verify(carRepository, times(1)).save(car);
    }

    @Test
    @DisplayName("Should edit car details successfully")
    void editCar_ShouldCallEditMethod() {
        Car car = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");

        carService.editCar(car);

        verify(carRepository, times(1)).edit(car);
    }

    @Test
    @DisplayName("Should delete car by VIN successfully")
    void deleteCar_ShouldCallDeleteMethod() {
        String vinNumber = "1HGCM82633A004352";

        carService.deleteCar(vinNumber);

        verify(carRepository, times(1)).delete(vinNumber);
    }

    @Test
    @DisplayName("Should return all cars from the repository")
    void getAllCars_ShouldReturnAllCars() {
        List<Car> cars = Arrays.asList(
                new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New"),
                new Car("1HGCM82633A004353", "Toyota", "Corolla", 2019, 18000.00, "Used")
        );
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getVinNumber()).isEqualTo("1HGCM82633A004352");
        assertThat(result.get(1).getVinNumber()).isEqualTo("1HGCM82633A004353");
    }

    @Test
    @DisplayName("Should return car by VIN if it exists")
    void getCar_ShouldReturnCar_WhenExists() {
        String vinNumber = "1HGCM82633A004352";
        Car car = new Car(vinNumber, "Honda", "Civic", 2020, 20000.00, "New");
        when(carRepository.findByVin(vinNumber)).thenReturn(Optional.of(car));

        Optional<Car> result = carService.getCar(vinNumber);

        assertThat(result).isPresent();
        assertThat(result.get().getVinNumber()).isEqualTo(vinNumber);
    }

    @Test
    @DisplayName("Should return empty if car does not exist")
    void getCar_ShouldReturnEmpty_WhenNotExists() {
        String vinNumber = "1HGCM82633A004352";
        when(carRepository.findByVin(vinNumber)).thenReturn(Optional.empty());

        Optional<Car> result = carService.getCar(vinNumber);

        assertThat(result).isEmpty();
    }
}