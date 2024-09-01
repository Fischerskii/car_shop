package ru.ylab.carshopapp.dto;

import lombok.*;
import ru.ylab.carshopapp.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private UUID id;
    private String username;
    private String carVinNumber;
    private OrderStatus status;
    private LocalDateTime orderCreationDate;
}