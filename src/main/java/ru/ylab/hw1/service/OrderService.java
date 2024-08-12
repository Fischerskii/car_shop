package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    void createOrder(Order order);

    void changeOrderStatus(UUID id, OrderStatus status);

    Optional<Order> getOrderById(UUID id);

    List<Order> getAllOrders();
}
