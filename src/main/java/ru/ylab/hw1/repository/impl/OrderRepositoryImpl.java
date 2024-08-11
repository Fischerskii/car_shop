package ru.ylab.hw1.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.config.DatabaseConfig;
import ru.ylab.hw1.dto.OrderDTO;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.exception.DataAccessException;
import ru.ylab.hw1.repository.AbstractRepository;
import ru.ylab.hw1.repository.OrderRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class OrderRepositoryImpl extends AbstractRepository implements OrderRepository {

    @Override
    public void save(OrderDTO orderDTO) {
        String query = "INSERT INTO order (id, user_name, car_vin_number, status, order_creation_date) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setObject(1, orderDTO.getId());
                statement.setString(2, orderDTO.getUserName());
                statement.setString(3, orderDTO.getCarVinNumber());
                statement.setString(4, String.valueOf(orderDTO.getStatus()));
                statement.setTimestamp(5, Timestamp.valueOf(orderDTO.getOrderCreationDate()));

                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error creating order with id: {}", orderDTO.getId(), e);
                throw new DataAccessException("Error creating order with id: " + orderDTO.getId(), e);
            }
        } catch (SQLException e) {
            log.error("Failed to save order", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void edit(UUID id, OrderStatus orderNewStatus) {
        String query = "UPDATE order SET status = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(orderNewStatus));
                statement.setObject(2, id);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    log.warn("Not found order with id: {}", id);
                } else {
                    log.info("Order with id: {} was changed status to: {}", id, orderNewStatus);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error updating order with id: {}", id, e);
                throw new DataAccessException("Error updating order with id: " + id, e);
            }
        } catch (SQLException e) {
            log.error("Failed to edit order", e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<OrderDTO> findAll() {
        String query = "SELECT id, user_name, car_vin_number, status, order_creation_date FROM order";
        List<OrderDTO> orderDTOS = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                OrderDTO orderDTO = mapRowToOrder(resultSet);

                orderDTOS.add(orderDTO);
            }
        } catch (SQLException e) {
            log.error("Error finding orders", e);
        }

        return orderDTOS;
    }

    @Override
    public Optional<OrderDTO> findById(UUID id) {
        String query = "SELECT * FROM order WHERE id = ?";

        OrderDTO orderDTO = null;
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, String.valueOf(id));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                orderDTO = mapRowToOrder(resultSet);
            }

        } catch (SQLException e) {
            log.error("Error finding order with id: {}", id, e);
        }

        return Optional.ofNullable(orderDTO);
    }

    private OrderDTO mapRowToOrder(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String userName = resultSet.getString("user_name");
        String carVinNumber = resultSet.getString("car_vin_number");
        OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
        LocalDateTime orderCreationDate = resultSet.getTimestamp("order_creation_date").toLocalDateTime();

        return OrderDTO.builder()
                .id(UUID.fromString(id))
                .userName(userName)
                .carVinNumber(carVinNumber)
                .status(status)
                .orderCreationDate(orderCreationDate)
                .build();
    }
}
