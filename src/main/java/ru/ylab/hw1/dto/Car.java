package ru.ylab.hw1.dto;

public record Car(String brand, String model, int year, double price, String condition) {

    @Override
    public String toString() {
        return String.format("Brand: %s, Model: %s, Year: %d, Price: %.2f, Condition: %s",
                brand, model, year, price, condition);
    }
}
