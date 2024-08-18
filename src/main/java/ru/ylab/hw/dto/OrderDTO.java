package ru.ylab.hw.dto;

import lombok.Data;
import ru.ylab.hw.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private String username;
    private String carVinNumber;
    private OrderStatus status;
    private LocalDateTime orderCreationDate;

    public OrderDTO(UUID id, String username, String carVinNumber, OrderStatus status, LocalDateTime orderCreationDate) {
        validateUsername(username);
        validateCarVinNumber(carVinNumber);
        validateStatus(status);

        this.id = id;
        this.username = username;
        this.carVinNumber = carVinNumber;
        this.status = status;
        this.orderCreationDate = orderCreationDate;
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    private void validateCarVinNumber(String carVinNumber) {
        if (carVinNumber == null || carVinNumber.length() != 17) {
            throw new IllegalArgumentException("Car VIN number is invalid");
        }
    }

    private void validateStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }

    private void validateOrderCreationDate(LocalDateTime orderCreationDate) {
        if (orderCreationDate == null) {
            throw new IllegalArgumentException("Order creation Date cannot be null");
        }
    }
}