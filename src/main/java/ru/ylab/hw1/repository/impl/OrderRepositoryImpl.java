package ru.ylab.hw1.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.config.DatabaseConfig;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.OrderRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    public void save(Order order) {
        String sql = "INSERT INTO order (client, car, status, orderDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, order.getId());
            statement.setObject(2, order.getUserName());
            statement.setString(3, order.getCarVinNumber());
            statement.setObject(4, order.getStatus());
            statement.setTimestamp(5, Timestamp.valueOf(order.getOrderCreationDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error creating with id: {}", order.getId(), e);
        }
    }

    public void edit(UUID id, OrderStatus orderNewStatus) {
        String sql = "UPDATE order SET status = ? WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, String.valueOf(orderNewStatus));
            statement.setObject(2, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.warn("Not found order with id: {}", id);
            } else {
                log.info("Order with id: {} was changed status to: {}", id, orderNewStatus);
            }
        } catch (SQLException e) {
            log.error("Error updating with id: {}", id, e);
        }
    }

    public List<Order> findAll() {
        String selectQuery = "SELECT id, user_name, car_vin_number, status, order_creation_date FROM order";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = mapRowToOrder(resultSet);

                orders.add(order);
            }
        } catch (SQLException e) {
            log.error("Error finding orders", e);
        }

        return orders;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        String sql = "SELECT * FROM order WHERE id = ?";

        Order order = null;
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, String.valueOf(id));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = mapRowToOrder(resultSet);
            }

        } catch (SQLException e) {
            log.error("Error finding order with id: {}", id, e);
        }

        return Optional.ofNullable(order);
    }

    private Order mapRowToOrder(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String userName = resultSet.getString("user_name");
        String carVinNumber = resultSet.getString("car_vin_number");
        OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
        LocalDateTime orderCreationDate = resultSet.getTimestamp("order_creation_date").toLocalDateTime();

        return Order.builder()
                .id(UUID.fromString(id))
                .userName(userName)
                .carVinNumber(carVinNumber)
                .status(status)
                .orderCreationDate(orderCreationDate)
                .build();
    }
}
