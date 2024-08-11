package ru.ylab.hw1.dto;

import lombok.Builder;
import lombok.Data;
import ru.ylab.hw1.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class OrderDTO {
    private UUID id;
    private String userName;
    private String carVinNumber;
    private OrderStatus status;
    private LocalDateTime orderCreationDate;
}
