package ru.ylab.carshopapp.entity;

import lombok.*;
import ru.ylab.carshopapp.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private UUID id;
    private String username;
    private String carVinNumber;
    private OrderStatus status;
    private LocalDateTime orderCreationDate;
}
