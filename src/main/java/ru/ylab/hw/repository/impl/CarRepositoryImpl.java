package ru.ylab.hw.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ylab.hw.config.DatabaseConfig;
import ru.ylab.hw.entity.Car;
import ru.ylab.hw.exception.DataAccessException;
import ru.ylab.hw.repository.CarRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CarRepositoryImpl implements CarRepository {

    public Car save(Car car) {
        String sql = "INSERT INTO entity_schema.car (car_vin_number, brand, model, year, price, condition) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "RETURNING car_vin_number";

        try (Connection connection = new DatabaseConfig().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                createStatement(car, statement);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String vin = resultSet.getString("car_vin_number");
                    car.setVinNumber(vin);
                    log.info("Car with VIN {} has been saved", vin);
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error creating car with VIN: {}", car.getVinNumber(), e);
            }
        } catch (SQLException e) {
            log.error("Database error when saving car", e);
            throw new DataAccessException("Error saving car", e);
        }
        return car;
    }

    public void edit(Car car) {
        String sql = "UPDATE entity_schema.car " +
                "SET brand = ?, model = ?, year = ?, price = ?, condition = ? " +
                "WHERE car_vin_number = ?";

        try (Connection connection = new DatabaseConfig().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                createStatement(car, statement);

                int rowUpdated = statement.executeUpdate();

                if (rowUpdated == 0) {
                    log.warn("Not found car with VIN: {}", car.getVinNumber());
                } else {
                    log.info("Car with VIN {} was updated successfully.", car.getVinNumber());
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error updating car with VIN: {}", car.getVinNumber(), e);
            }
        } catch (SQLException e) {
            log.error("Database error when updating car", e);
            throw new DataAccessException("Error updating car", e);
        }
    }

    public void delete(String vinNumber) {
        String sql = "DELETE FROM entity_schema.car " +
                "WHERE id = ?";

        try (Connection connection = new DatabaseConfig().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vinNumber);
                statement.executeUpdate();
                log.info("Car with VIN {} was deleted successfully.", vinNumber);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error deleting car with VIN: {}", vinNumber, e);
            }
        } catch (SQLException e) {
            log.error("Database error when deleting car", e);
            throw new DataAccessException("Error deleting car", e);
        }
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.car";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Car car = new Car();
                car.setVinNumber(resultSet.getString("car_vin_number"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setPrice(resultSet.getDouble("price"));
                car.setCondition(resultSet.getString("condition"));

                cars.add(car);
            }
        } catch (SQLException e) {
            log.error("Error fetching cars", e);
        }
        return cars;
    }

    public Optional<Car> findByVin(String vinNumber) {
        String sql = "SELECT brand, model, year, price, condition " +
                "FROM entity_schema.car " +
                "WHERE car_vin_number = ?";

        try (Connection connection = new DatabaseConfig().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vinNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    double price = resultSet.getDouble("price");
                    String condition = resultSet.getString("condition");

                    Car car = new Car(vinNumber, brand, model, year, price, condition);
                    return Optional.of(car);
                }
            }

        } catch (SQLException e) {
            log.error("Error fetching car with VIN: {}", vinNumber, e);
        }

        return Optional.empty();
    }

    /**
     * Fills a {@link PreparedStatement} with the attributes of a {@link Car} entity.
     *
     * @param car       the car entity
     * @param statement the prepared statement to fill
     * @throws SQLException if there is an error setting a parameter in the statement
     */
    private void createStatement(Car car, PreparedStatement statement) throws SQLException {
        statement.setString(1, car.getVinNumber());
        statement.setString(2, car.getBrand());
        statement.setString(3, car.getModel());
        statement.setInt(4, car.getYear());
        statement.setDouble(5, car.getPrice());
        statement.setString(6, car.getCondition());
    }
}
