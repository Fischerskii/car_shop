package ru.ylab.carshopapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylab.carshopapp.dto.CarDTO;
import ru.ylab.carshopapp.entity.Car;
import ru.ylab.carshopapp.mapper.CarMapper;
import ru.ylab.carshopapp.service.CarService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
@Tag(name = "Cars", description = "Operations related to cars")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @PostMapping("/create")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO) {
        try {
            carService.addCar(carMapper.toEntity(carDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body("Car added successfully");
        } catch (IllegalArgumentException e) {
            log.error("Error adding car", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{vin}")
    public ResponseEntity<?> updateCar(@PathVariable String vin, @RequestBody CarDTO carDTO) {
        try {
            carDTO.setVinNumber(vin);
            carService.editCar(carMapper.toEntity(carDTO));
            return ResponseEntity.ok().body("Car updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{vin}")
    public ResponseEntity<?> deleteCar(@PathVariable String vin) {
        carService.deleteCar(vin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{vin}")
    public ResponseEntity<?> getCar(@PathVariable String vin) {
        Optional<Car> car = carService.getCar(vin);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }
}
