package ru.ylab.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String vinNumber;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;

    @Override
    public String toString() {
        return String.format("VIN: %s, Brand: %s, Model: %s, Year: %d, Price: %.2f, Condition: %s",
                vinNumber, brand, model, year, price, condition);
    }
}
