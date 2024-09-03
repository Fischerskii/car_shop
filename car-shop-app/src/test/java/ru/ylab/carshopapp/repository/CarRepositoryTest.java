package ru.ylab.carshopapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ylab.carshopapp.BaseTest;
import ru.ylab.carshopapp.entity.Car;
import ru.ylab.carshopapp.repository.impl.CarRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarRepositoryTest extends BaseTest {

    @Autowired
    private CarRepositoryImpl carRepository;

    @BeforeEach
    public void setup() {
        // Очистка таблицы перед каждым тестом
        carRepository.delete("WVGZZZCAZJC552497");
        carRepository.save(new Car("WVGZZZCAZJC552497", "Toyota", "Camry", 2022, 30000, "new"));
    }

    @Test
    public void testSave() {
        Car car = new Car("JHMCM56557C404453", "Honda", "Civic", 2020, 25000, "used");
        carRepository.save(car);

        Optional<Car> foundCar = carRepository.findByVin("JHMCM56557C404453");
        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getBrand()).isEqualTo("Honda");
    }

    @Test
    public void testEdit() {
        Car car = new Car("WVGZZZCAZJC552497", "Toyota", "Corolla", 2021, 28000, "used");
        carRepository.edit(car);

        Optional<Car> updatedCar = carRepository.findByVin("WVGZZZCAZJC552497");
        assertThat(updatedCar).isPresent();
        assertThat(updatedCar.get().getModel()).isEqualTo("Corolla");
    }

    @Test
    public void testDelete() {
        carRepository.delete("WVGZZZCAZJC552497");

        Optional<Car> deletedCar = carRepository.findByVin("WVGZZZCAZJC552497");
        assertThat(deletedCar).isNotPresent();
    }

    @Test
    public void testFindAll() {
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getVinNumber()).isEqualTo("WVGZZZCAZJC552497");
    }

    @Test
    public void testFindByVin() {
        Optional<Car> car = carRepository.findByVin("WVGZZZCAZJC552497");
        assertThat(car).isPresent();
        assertThat(car.get().getBrand()).isEqualTo("Toyota");
    }

    @Test
    public void testFindByVin_NotFound() {
        Optional<Car> car = carRepository.findByVin("NON_EXISTENT_VIN");
        assertThat(car).isNotPresent();
    }
}