package ru.ylab.hw1.dto;

import lombok.Getter;

@Getter
public class Car {
    private final String brand;
    private final String model;
    private final int year;
    private final double price;
    private final String condition;

    public Car(String brand, String model, int year, double price, String condition) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return String.format("Brand: %s, Model: %s, Year: %d, Price: %.2f, Condition: %s",
                brand, model, year, price, condition);
    }
}
