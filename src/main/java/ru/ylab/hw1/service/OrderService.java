package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.OrderDTO;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    void createOrder(OrderDTO orderDTO);

    void changeOrderStatus(UUID id, OrderStatus status);

    Optional<OrderDTO> getOrderById(UUID id);

    List<OrderDTO> getAllOrders();
}
