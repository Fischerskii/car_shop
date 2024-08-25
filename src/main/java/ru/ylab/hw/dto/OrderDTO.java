package ru.ylab.hw.dto;

import lombok.*;
import ru.ylab.hw.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private String username;
    private String carVinNumber;
    private OrderStatus status;
    private LocalDateTime orderCreationDate;
}