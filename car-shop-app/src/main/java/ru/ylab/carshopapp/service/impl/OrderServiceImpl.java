package ru.ylab.carshopapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ylab.auditlogging.annotation.Audit;
import ru.ylab.carshopapp.enums.OrderStatus;
import ru.ylab.carshopapp.repository.OrderRepository;
import ru.ylab.carshopapp.service.OrderService;
import ru.ylab.carshopapp.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.ylab.common.enums.ActionType.ORDER_ACTIONS;

@Service
@Slf4j
@Audit(actionType = ORDER_ACTIONS)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
        log.info("Order with id {} has been created", order.getId());
    }

    @Override
    public void changeOrderStatus(UUID id, OrderStatus status) {
        orderRepository.edit(id, status);
        log.info("Order with id {} has been changed to status {}", id, status);
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
