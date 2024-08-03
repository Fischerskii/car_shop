package ru.ylab.hw1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private User customer;
    private Car car;
    private String status;

    public Order(User customer, Car car) {
        this.customer = customer;
        this.car = car;
        this.status = "Pending";
    }
}
