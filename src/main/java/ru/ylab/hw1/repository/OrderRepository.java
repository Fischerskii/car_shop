package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
    }

    public void edit(int id, Order.OrderStatus status) {
        for (Order order : orders) {
            if (order.getId() == id) {
                order.setStatus(status);
                break;
            }
        }
    }

    public List<Order> findAll() {
        return orders;
    }
}
