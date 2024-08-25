package ru.ylab.hw.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylab.hw.dto.CarDTO;
import ru.ylab.hw.entity.Car;
import ru.ylab.hw.mapper.CarMapper;
import ru.ylab.hw.service.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO) {
        try {
            carService.addCar(carMapper.toEntity(carDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body("Car added successfully");
        } catch (IllegalArgumentException e) {
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
