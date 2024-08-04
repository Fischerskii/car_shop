package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;

import java.util.List;

public interface OrderService {

    void createOrder(User client, Car car);

    void changeOrderStatus(int id, Order.OrderStatus status);

    void viewOrders();

    List<Order> getAllOrders();
}
