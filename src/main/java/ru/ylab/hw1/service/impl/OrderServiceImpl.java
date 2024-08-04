package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final List<Order> orders = new ArrayList<>();

    public void createOrder(User client, Car car) {
        orders.add(new Order(client, car));
    }

    public void changeOrderStatus(int id, Order.OrderStatus status) {
        for (Order order : orders) {
            if (order.getId() == id) {
                order.setStatus(status);
                break;
            }
        }
    }

    public void viewOrders() {
        orders.forEach(System.out::println);
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}
