package ru.ylab.hw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.ylab.hw.BaseTest;
import ru.ylab.hw.config.AppConfig;
import ru.ylab.hw.dto.CarDTO;
import ru.ylab.hw.entity.Car;
import ru.ylab.hw.mapper.CarMapper;
import ru.ylab.hw.service.CarService;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@ContextConfiguration(classes = {AppConfig.class, CarController.class})
@WebAppConfiguration
class CarControllerTest extends BaseTest {

    @Mock
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add car successfully")
    void testAddCar_Success() {
        CarDTO carDTO = new CarDTO(
                "1HGCM82633A004352",
                "Toyota",
                "Camry",
                2020,
                20000,
                "new"
                );

        Car car = new Car();
        car.setVinNumber("1HGCM82633A004352");
        car.setBrand("Toyota");
        car.setModel("Camry");
        car.setYear(2020);
        car.setPrice(20000);
        car.setCondition("new");

        when(carMapper.toEntity(carDTO)).thenReturn(car);
        doNothing().when(carService).addCar(car);

        ResponseEntity<?> response = carController.addCar(carDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Car added successfully");
        verify(carService, times(1)).addCar(car);
    }

    @Test
    @DisplayName("Update car successfully")
    void testUpdateCar_Success() {
        CarDTO carDTO = new CarDTO(
                "1HGCM82633A004352",
                "Honda",
                "Accord",
                2022,
                20000,
                "new"
        );

        Car car = new Car();
        car.setVinNumber("1HGCM82633A004352");
        car.setBrand("Honda");
        car.setModel("Accord");
        car.setYear(2022);
        car.setPrice(20000);
        car.setCondition("new");

        when(carMapper.toEntity(carDTO)).thenReturn(car);
        doNothing().when(carService).editCar(car);

        ResponseEntity<?> response = carController.updateCar(carDTO.getVinNumber(), carDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Car updated successfully");
        verify(carService, times(1)).editCar(car);
    }

    @Test
    @DisplayName("Delete car successfully")
    void testDeleteCar_Success() {
        String vin = "1HGCM82633A004352";

        doNothing().when(carService).deleteCar(vin);

        ResponseEntity<?> response = carController.deleteCar(vin);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(carService, times(1)).deleteCar(vin);
    }

    @Test
    @DisplayName("Get car by VIN - car found")
    void testGetCar_Found() {
        String vin = "1HGCM82633A004352";
        Car car = new Car();
        car.setVinNumber(vin);
        car.setBrand("Toyota");
        car.setModel("Camry");
        car.setYear(2020);
        car.setPrice(20000);
        car.setCondition("new");

        when(carService.getCar(vin)).thenReturn(Optional.of(car));

        ResponseEntity<?> response = carController.getCar(vin);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(car);
        verify(carService, times(1)).getCar(vin);
    }

    @Test
    @DisplayName("Get car by VIN - car not found")
    void testGetCar_NotFound() {
        String vin = "1HGCM82633A004352";

        when(carService.getCar(vin)).thenReturn(Optional.empty());

        ResponseEntity<?> response = carController.getCar(vin);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(carService, times(1)).getCar(vin);
    }

    @Test
    @DisplayName("Get all cars successfully")
    void testGetAllCars() {
        Car car1 = new Car();
        car1.setVinNumber("1HGCM82633A004352");
        car1.setBrand("Toyota");
        car1.setModel("Camry");
        car1.setYear(2020);
        car1.setPrice(20000);
        car1.setCondition("new");

        Car car2 = new Car();
        car2.setVinNumber("2HGCM82633A004352");
        car2.setBrand("Honda");
        car2.setModel("Accord");
        car2.setYear(2022);
        car2.setPrice(10000);
        car2.setCondition("new");

        List<Car> cars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        ResponseEntity<List<Car>> response = carController.getAllCars();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(cars);
        verify(carService, times(1)).getAllCars();
    }
}