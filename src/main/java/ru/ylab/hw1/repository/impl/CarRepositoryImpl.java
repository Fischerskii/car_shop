package ru.ylab.hw1.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.config.DatabaseConfig;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.repository.CarRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CarRepositoryImpl implements CarRepository {

    public Car save(Car car) {
        String sql = "INSERT INTO car (brand, model, year, price, condition) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            createStatement(car, statement);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String vin = resultSet.getString("vin");
                car.setVinNumber(vin);
                log.info("Car with vin {} has been saved", vin);
            }
        } catch (SQLException e) {
            log.error("Error creating car with VIN: {}", car.getVinNumber(), e);
        }

        return car;
    }

    public void edit(Car car) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ?, price = ?, condition = ? WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            createStatement(car, statement);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.warn("Not found car with VIN: {}", car.getVinNumber());
            } else {
                log.info("Car with VIN {} was updated successfully.", car.getVinNumber());
            }

        } catch (SQLException e) {
            log.error("Error updating car with VIN: {}", car.getVinNumber(), e);
        }
    }

    public void delete(String vinNumber) {
        String sql = "DELETE FROM cars WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, vinNumber);
            statement.executeUpdate();
            log.info("Car with VIN {} was deleted successfully.", vinNumber);
        } catch (SQLException e) {
            log.error("Error deleting car with VIN: {}", vinNumber, e);
        }
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Car car = new Car();
                car.setVinNumber(resultSet.getString("vin"));
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
        String sql = "SELECT id, brand, model, year, price, condition FROM car WHERE vin = ?";

        try (Connection connection = DatabaseConfig.getConnection();
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

    private void createStatement(Car car, PreparedStatement statement) throws SQLException {
        statement.setString(1, car.getVinNumber());
        statement.setString(2, car.getBrand());
        statement.setString(3, car.getModel());
        statement.setInt(4, car.getYear());
        statement.setDouble(5, car.getPrice());
        statement.setString(6, car.getCondition());
    }
}
