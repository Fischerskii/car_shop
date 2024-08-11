package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(Order order);

    void edit(UUID id, OrderStatus orderNewStatus);

    List<Order> findAll();

    Optional<Order> findById(UUID id);
}
