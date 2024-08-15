//package ru.ylab.hw1.service.impl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ru.ylab.hw1.dto.Car;
//import ru.ylab.hw1.repository.impl.CarRepositoryImpl;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//class CarServiceTest {
//
//    @Mock
//    private CarRepositoryImpl carRepository;
//
//    @InjectMocks
//    private CarServiceImpl carService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Тест на сохранение авто")
//    void addCar_ShouldSaveCar() {
//        Car car = new Car("vinNumber", "Toyota", "Camry", 2020, 30000, "New");
//        when(carRepository.save(car)).thenReturn(car);
//
//        Car savedCar = carService.addCar(car);
//
//        assertThat(savedCar).isEqualTo(car);
//        verify(carRepository, times(1)).save(car);
//    }
//
//    @Test
//    @DisplayName("Тест на редактирвоание авто")
//    void editCar_ShouldEditCar() {
//        Car updatedCar = new Car("vinNumber", "Honda", "Accord", 2021, 32000, "New");
//
//        carService.editCar(updatedCar);
//
//        verify(carRepository, times(1)).edit(updatedCar);
//    }
//
//    @Test
//    @DisplayName("Проверка работоспособности функционала удаления авто")
//    void deleteCar_ShouldDeleteCar() {
//        carService.deleteCar("vinNumber");
//
//        verify(carRepository, times(1)).delete("vinNumber");
//    }
//
//    @Test
//    @DisplayName("Проверка выдачи всего списка автомобилей")
//    void getAllCars_ShouldReturnAllCars() {
//        Car car1 = new Car("vinNumber","Toyota", "Camry", 2020, 30000, "New");
//        Car car2 = new Car("vinNumber2", "Honda", "Accord", 2021, 32000, "New");
//        when(carRepository.findAll()).thenReturn(List.of(car1, car2));
//
//        List<Car> cars = carService.getAllCars();
//
//        assertThat(cars).containsExactly(car1, car2);
//        verify(carRepository, times(1)).findAll();
//    }
//}