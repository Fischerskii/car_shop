package ru.ylab.hw1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    private String brand;
    private String model;
    private int year;
    private int price;
    private String condition;
    private String vinNumber;

    @Override
    public String toString() {
        return String.format("Brand: %s, Model: %s, Year: %d, Price: %d, Condition: %s, VIN number: %s",
                brand, model, year, price, condition, vinNumber);
    }
}
