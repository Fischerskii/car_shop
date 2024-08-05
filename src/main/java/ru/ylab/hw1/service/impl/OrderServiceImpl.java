package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.repository.OrderRepository;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(User client, Car car) {
        orderRepository.save(new Order(client, car));
    }

    public void changeOrderStatus(int id, Order.OrderStatus status) {
        orderRepository.edit(id, status);
    }

    public void viewOrders() {
        getAllOrders().forEach(System.out::println);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
