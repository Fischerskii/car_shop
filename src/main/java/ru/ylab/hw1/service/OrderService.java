package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.Map;

public interface OrderService {

    void createOrder(User client, Car car);

    void changeOrderStatus(int id, OrderStatus status);

    Map<Integer, Order> getAllOrders();
}
