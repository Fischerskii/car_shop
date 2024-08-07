package ru.ylab.hw1.dto;

import lombok.Data;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.Date;

@Data
public class Order {
    private int id;
    private User client;
    private Car car;
    private OrderStatus status;
    private Date orderDate;

    public Order(User client, Car car) {
        this.client = client;
        this.car = car;
        this.status = OrderStatus.PENDING;
        this.orderDate = new Date();
    }
}
