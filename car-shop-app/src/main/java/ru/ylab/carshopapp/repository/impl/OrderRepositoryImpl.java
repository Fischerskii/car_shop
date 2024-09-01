package ru.ylab.carshopapp.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.carshopapp.entity.Order;
import ru.ylab.carshopapp.enums.OrderStatus;
import ru.ylab.carshopapp.exception.DataAccessException;
import ru.ylab.carshopapp.repository.OrderRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Order order) {
        String query = "INSERT INTO entity_schema.order (id, username, car_vin_number, status, order_creation_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(query, order.getId(), order.getUsername(), order.getCarVinNumber(),
                    order.getStatus().name(), Timestamp.valueOf(order.getOrderCreationDate()));
            log.info("Order with id: {} has been saved", order.getId());
        } catch (DataAccessException e) {
            log.error("Error creating order with id: {}", order.getId(), e);
            throw e;
        }
    }

    @Override
    public void edit(UUID id, OrderStatus orderNewStatus) {
        String query = "UPDATE entity_schema.order SET status = ? WHERE id = ?";

        try {
            int rowsUpdated = jdbcTemplate.update(query, orderNewStatus.name(), id);
            if (rowsUpdated == 0) {
                log.warn("Not found order with id: {}", id);
            } else {
                log.info("Order with id: {} was changed status to: {}", id, orderNewStatus);
            }
        } catch (DataAccessException e) {
            log.error("Error updating order with id: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT id, username, car_vin_number, status, order_creation_date FROM entity_schema.order";
        return jdbcTemplate.query(query, (rs, rowNum) -> mapRowToOrder(rs));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        String query = "SELECT * FROM entity_schema.order WHERE id = ?";
        try {
            Order order = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> mapRowToOrder(rs));
            return Optional.ofNullable(order);
        } catch (DataAccessException e) {
            log.error("Error finding order with id: {}", id, e);
            return Optional.empty();
        }
    }

    private Order mapRowToOrder(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String userName = resultSet.getString("username");
        String carVinNumber = resultSet.getString("car_vin_number");
        OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
        LocalDateTime orderCreationDate = resultSet.getTimestamp("order_creation_date").toLocalDateTime();

        return Order.builder()
                .id(UUID.fromString(id))
                .username(userName)
                .carVinNumber(carVinNumber)
                .status(status)
                .orderCreationDate(orderCreationDate)
                .build();
    }
}

