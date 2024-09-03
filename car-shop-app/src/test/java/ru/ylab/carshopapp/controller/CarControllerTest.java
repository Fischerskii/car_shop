package ru.ylab.carshopapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ylab.carshopapp.BaseTest;
import ru.ylab.carshopapp.dto.CarDTO;
import ru.ylab.carshopapp.entity.Car;
import ru.ylab.carshopapp.mapper.CarMapper;
import ru.ylab.carshopapp.service.CarService;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    private CarDTO carDTO;
    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carDTO = new CarDTO("WVGZZZCAZJC552497", "Toyota", "Corolla", 2022, 10000, "new");
        car = new Car("WVGZZZCAZJC552497", "Toyota", "Corolla", 2022, 10000, "new");
    }

    @Test
    void addCar_ShouldReturnCreated() throws Exception {
        when(carMapper.toEntity(any(CarDTO.class))).thenReturn(car);

        mockMvc.perform(post("/api/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vinNumber\":\"WVGZZZCAZJC552497\",\"make\":\"Toyota\",\"model\":\"Corolla\",\"year\":2022}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Car added successfully"));
    }

    @Test
    void updateCar_ShouldReturnOk() throws Exception {
        when(carMapper.toEntity(any(CarDTO.class))).thenReturn(car);

        mockMvc.perform(put("/api/cars/WVGZZZCAZJC552497")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vinNumber\":\"WVGZZZCAZJC552497\",\"make\":\"Toyota\",\"model\":\"Corolla\",\"year\":2022}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car updated successfully"));
    }

    @Test
    void deleteCar_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/cars/WVGZZZCAZJC552497"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCar_ShouldReturnOk() throws Exception {
        when(carService.getCar(anyString())).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/cars/WVGZZZCAZJC552497"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vinNumber").value("WVGZZZCAZJC552497"))
                .andExpect(jsonPath("$.make").value("Toyota"));
    }

    @Test
    void getAllCars_ShouldReturnOk() throws Exception {
        List<Car> cars = List.of(car);
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vinNumber").value("WVGZZZCAZJC552497"))
                .andExpect(jsonPath("$[0].make").value("Toyota"));
    }
}
