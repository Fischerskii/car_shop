package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.OrderRepository;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;

import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(User client, Car car) {
        orderRepository.save(new Order(client, car));
    }

    public void changeOrderStatus(int id, OrderStatus status) {
        orderRepository.edit(id, status);
    }

    public Map<Integer, Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
