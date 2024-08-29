package ru.ylab.hw.entity;

import lombok.*;
import ru.ylab.hw.enums.OrderStatus;

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
