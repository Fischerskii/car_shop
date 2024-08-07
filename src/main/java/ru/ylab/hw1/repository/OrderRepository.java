package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.Map;

public interface OrderRepository {

    void save(Order order);

    void edit(int id, OrderStatus status);

    Map<Integer, Order> findAll();
}
