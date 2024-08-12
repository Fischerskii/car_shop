package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.repository.OrderRepository;
import ru.ylab.hw1.service.OrderService;
import ru.ylab.hw1.dto.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the {@link OrderService} interface that provides
 * methods for creating, updating, and retrieving orders in the car dealership application.
 */
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    /**
     * @param orderRepository the order repository to interact with the data source
     */
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order by saving it to the data source.
     *
     * @param order the data transfer object representing the order to be created
     */
    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
        log.info("Order with id {} has been created", order.getId());
    }

    /**
     * Changes the status of an existing order.
     * Logs the status change.
     *
     * @param id     the unique identifier of the order
     * @param status the new status to be applied to the order
     */
    @Override
    public void changeOrderStatus(UUID id, OrderStatus status) {
        orderRepository.edit(id, status);
        log.info("Order with id {} has been changed to status {}", id, status);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return an {@link Optional} containing the {@link Order} if found, or an empty Optional if not found
     */
    @Override
    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    /**
     * Retrieves a list of all orders from the repository.
     *
     * @return a list of {@link Order} objects representing all orders
     */
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
