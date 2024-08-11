package ru.ylab.hw1.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.config.DatabaseConfig;
import ru.ylab.hw1.dto.CarDTO;
import ru.ylab.hw1.exception.DataAccessException;
import ru.ylab.hw1.repository.AbstractRepository;
import ru.ylab.hw1.repository.CarRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CarRepositoryImpl extends AbstractRepository implements CarRepository {

    public CarDTO save(CarDTO carDTO) {
        String sql = "INSERT INTO car (brand, model, year, price, condition) VALUES (?, ?, ?, ?, ?) RETURNING id";

        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                createStatement(carDTO, statement);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String vin = resultSet.getString("vin");
                    carDTO.setVinNumber(vin);
                    log.info("Car with VIN {} has been saved", vin);
                }

                connection.commit();
            } catch (SQLException e) {
                rollbackConnection(connection);
                log.error("Error creating car with VIN: {}", carDTO.getVinNumber(), e);
            }
        } catch (SQLException e) {
            log.error("Database error when saving car", e);
            throw new DataAccessException("Error saving car", e);
        } finally {
            closeConnection(connection);
        }

        return carDTO;
    }

    public void edit(CarDTO carDTO) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ?, price = ?, condition = ? WHERE id = ?";

        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                createStatement(carDTO, statement);

                int rowUpdated = statement.executeUpdate();

                if (rowUpdated == 0) {
                    log.warn("Not found car with VIN: {}", carDTO.getVinNumber());
                } else {
                    log.info("Car with VIN {} was updated successfully.", carDTO.getVinNumber());
                }

                connection.commit();
            } catch (SQLException e) {
                rollbackConnection(connection);
                log.error("Error updating car with VIN: {}", carDTO.getVinNumber(), e);
            }
        } catch (SQLException e) {
            log.error("Database error when updating car", e);
            throw new DataAccessException("Error updating car", e);
        } finally {
            closeConnection(connection);
        }
    }

    public void delete(String vinNumber) {
        String sql = "DELETE FROM cars WHERE id = ?";

        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vinNumber);
                statement.executeUpdate();
                log.info("Car with VIN {} was deleted successfully.", vinNumber);

                connection.commit();
            } catch (SQLException e) {
                rollbackConnection(connection);
                log.error("Error deleting car with VIN: {}", vinNumber, e);
            }
        } catch (SQLException e) {
            log.error("Database error when deleting car", e);
            throw new DataAccessException("Error deleting car", e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<CarDTO> findAll() {
        List<CarDTO> carDTOS = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CarDTO carDTO = new CarDTO();
                carDTO.setVinNumber(resultSet.getString("vin"));
                carDTO.setBrand(resultSet.getString("brand"));
                carDTO.setModel(resultSet.getString("model"));
                carDTO.setYear(resultSet.getInt("year"));
                carDTO.setPrice(resultSet.getDouble("price"));
                carDTO.setCondition(resultSet.getString("condition"));

                carDTOS.add(carDTO);
            }
        } catch (SQLException e) {
            log.error("Error fetching cars", e);
        }
        return carDTOS;
    }

    public Optional<CarDTO> findByVin(String vinNumber) {
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

                    CarDTO carDTO = new CarDTO(vinNumber, brand, model, year, price, condition);
                    return Optional.of(carDTO);
                }
            }

        } catch (SQLException e) {
            log.error("Error fetching car with VIN: {}", vinNumber, e);
        }

        return Optional.empty();
    }

    private void createStatement(CarDTO carDTO, PreparedStatement statement) throws SQLException {
        statement.setString(1, carDTO.getVinNumber());
        statement.setString(2, carDTO.getBrand());
        statement.setString(3, carDTO.getModel());
        statement.setInt(4, carDTO.getYear());
        statement.setDouble(5, carDTO.getPrice());
        statement.setString(6, carDTO.getCondition());
    }
}
