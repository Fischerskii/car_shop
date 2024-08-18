package ru.ylab.hw.entity;

import lombok.Builder;
import lombok.Data;
import ru.ylab.hw.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Order {
    private UUID id;
    private String userName;
    private String carVinNumber;
    private OrderStatus status;
    private LocalDateTime orderCreationDate;
}
