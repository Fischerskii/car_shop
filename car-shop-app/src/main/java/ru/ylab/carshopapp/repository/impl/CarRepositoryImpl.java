package ru.ylab.carshopapp.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.carshopapp.entity.Car;
import ru.ylab.carshopapp.exception.DataAccessException;
import ru.ylab.carshopapp.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class CarRepositoryImpl implements CarRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car save(Car car) {
        String sql = "INSERT INTO entity_schema.car (car_vin_number, brand, model, year, price, condition) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING car_vin_number";

        try {
            String vin = jdbcTemplate.queryForObject(sql, new Object[]{
                    car.getVinNumber(), car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getCondition()
            }, String.class);

            car.setVinNumber(vin);
            log.info("Car with VIN {} has been saved", vin);
        } catch (DataAccessException e) {
            log.error("Error saving car with VIN: {}", car.getVinNumber(), e);
            throw e; // Перехватываем и перекидываем исключение для верхнего уровня
        }

        return car;
    }

    @Override
    public void edit(Car car) {
        String sql = "UPDATE entity_schema.car " +
                "SET brand = ?, model = ?, year = ?, price = ?, condition = ? " +
                "WHERE car_vin_number = ?";

        try {
            int rowsUpdated = jdbcTemplate.update(sql,
                    car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getCondition(), car.getVinNumber());

            if (rowsUpdated == 0) {
                log.warn("No car found with VIN: {}", car.getVinNumber());
            } else {
                log.info("Car with VIN {} was updated successfully.", car.getVinNumber());
            }
        } catch (DataAccessException e) {
            log.error("Error updating car with VIN: {}", car.getVinNumber(), e);
            throw e;
        }
    }

    @Override
    public void delete(String vinNumber) {
        String sql = "DELETE FROM entity_schema.car WHERE car_vin_number = ?";

        try {
            int rowsDeleted = jdbcTemplate.update(sql, vinNumber);
            if (rowsDeleted > 0) {
                log.info("Car with VIN {} was deleted successfully.", vinNumber);
            } else {
                log.warn("No car found with VIN: {}", vinNumber);
            }
        } catch (DataAccessException e) {
            log.error("Error deleting car with VIN: {}", vinNumber, e);
            throw e;
        }
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT car_vin_number, brand, model, year, price, condition FROM entity_schema.car";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Car(
                rs.getString("car_vin_number"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("price"),
                rs.getString("condition")
        ));
    }

    @Override
    public Optional<Car> findByVin(String vinNumber) {
        String sql = "SELECT car_vin_number, brand, model, year, price, condition " +
                "FROM entity_schema.car WHERE car_vin_number = ?";

        try {
            Car car = jdbcTemplate.queryForObject(sql, new Object[]{vinNumber}, (rs, rowNum) -> new Car(
                    rs.getString("car_vin_number"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getDouble("price"),
                    rs.getString("condition")
            ));
            return Optional.ofNullable(car);
        } catch (DataAccessException e) {
            log.error("Error fetching car with VIN: {}", vinNumber, e);
            return Optional.empty();
        }
    }
}

