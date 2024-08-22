package ru.ylab.hw.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.hw.BaseTest;
import ru.ylab.hw.entity.Car;
import ru.ylab.hw.repository.impl.CarRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CarRepositoryTest extends BaseTest {
    private Connection connection;
    private CarRepository carRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
        connection.setAutoCommit(false);
        carRepository = new CarRepositoryImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("Should save car and persist data")
    void save_ShouldPersistCar() {
        Car car = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");

        carRepository.save(car);

        Optional<Car> foundCar = carRepository.findByVin("1HGCM82633A004352");

        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getVinNumber()).isEqualTo("1HGCM82633A004352");
    }

    @Test
    @DisplayName("Should update car details successfully")
    void edit_ShouldUpdateCarDetails() {
        Car car = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");
        carRepository.save(car);

        car.setPrice(21000.00);
        carRepository.edit(car);

        Optional<Car> updatedCar = carRepository.findByVin("1HGCM82633A004352");

        assertThat(updatedCar).isPresent();
        assertThat(updatedCar.get().getPrice()).isEqualTo(21000.00);
    }

    @Test
    @DisplayName("Should delete car from the database")
    void delete_ShouldRemoveCar() {
        Car car = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");
        carRepository.save(car);

        carRepository.delete("1HGCM82633A004352");

        Optional<Car> deletedCar = carRepository.findByVin("1HGCM82633A004352");

        assertThat(deletedCar).isEmpty();
    }

    @Test
    @DisplayName("Should find car by VIN if it exists")
    void findByVin_ShouldReturnCar_WhenCarExists() {
        Car car = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");
        carRepository.save(car);

        Optional<Car> foundCar = carRepository.findByVin("1HGCM82633A004352");

        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getVinNumber()).isEqualTo("1HGCM82633A004352");
    }

    @Test
    @DisplayName("Should return all cars from the database")
    void findAll_ShouldReturnAllPersistedCars() {
        Car car1 = new Car("1HGCM82633A004352", "Honda", "Civic", 2020, 20000.00, "New");
        Car car2 = new Car("1HGCM82633A004353", "Toyota", "Corolla", 2019, 18000.00, "Used");
        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(2);
        assertThat(cars.get(0).getVinNumber()).isEqualTo("1HGCM82633A004352");
        assertThat(cars.get(1).getVinNumber()).isEqualTo("1HGCM82633A004353");
    }

}
