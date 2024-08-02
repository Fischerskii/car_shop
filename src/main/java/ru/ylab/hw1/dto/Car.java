package ru.ylab.hw1.dto;

import lombok.Data;
import ru.ylab.hw1.enums.CarCondition;

@Data
public class Car {
    private String brand;
    private String model;
    private int year;
    private int price;
    private CarCondition condition;
}
