package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.OrderRepository;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.dto.OrderDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        orderRepository.save(orderDTO);
        log.info("Order with id {} has been created", orderDTO.getId());
    }

    @Override
    public void changeOrderStatus(UUID id, OrderStatus status) {
        orderRepository.edit(id, status);
        log.info("Order with id {} has been changed to status {}", id, status);
    }

    @Override
    public Optional<OrderDTO> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll();
    }
}
