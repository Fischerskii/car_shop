package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.OrderDTO;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(OrderDTO orderDTO);

    void edit(UUID id, OrderStatus orderNewStatus);

    List<OrderDTO> findAll();

    Optional<OrderDTO> findById(UUID id);
}
