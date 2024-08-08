package ru.ylab.hw1.repository.impl;

import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepository {
    private final Map<Integer,Order> orders = new HashMap<>();
    private static int idCounter = 1;

    public void save(Order order) {
        orders.put(idCounter, order);
        idCounter++;
    }

    public void edit(int id, OrderStatus status) {
        Order order = orders.get(id);
        order.setStatus(status);
        orders.replace(id, order);
    }

    public Map<Integer, Order> findAll() {
        return new HashMap<>(orders);
    }
}
