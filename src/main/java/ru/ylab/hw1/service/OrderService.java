package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the {@link OrderService} interface that provides
 * methods for creating, updating, and retrieving orders in the car dealership application.
 */
public interface OrderService {

    /**
     * Creates a new order by saving it to the data source.
     *
     * @param order the data transfer object representing the order to be created
     */
    void createOrder(Order order);

    /**
     * Changes the status of an existing order.
     * Logs the status change.
     *
     * @param id     the unique identifier of the order
     * @param status the new status to be applied to the order
     */
    void changeOrderStatus(UUID id, OrderStatus status);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return an {@link Optional} containing the {@link Order} if found, or an empty Optional if not found
     */
    Optional<Order> getOrderById(UUID id);

    /**
     * Retrieves a list of all orders from the repository.
     *
     * @return a list of {@link Order} objects representing all orders
     */
    List<Order> getAllOrders();
}
