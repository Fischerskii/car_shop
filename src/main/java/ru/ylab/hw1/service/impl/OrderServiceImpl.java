package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.OrderRepository;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;

import java.util.Map;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(User client, Car car) {
        orderRepository.save(new Order(client, car));
        log.info("Order from client {} with car {} has been created", client, car);
    }

    public void changeOrderStatus(int id, OrderStatus status) {
        orderRepository.edit(id, status);
        log.info("Order with id {} has been changed to status {}", id, status);
    }

    public Map<Integer, Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
