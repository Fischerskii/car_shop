package ru.ylab.hw.repository;

import ru.ylab.hw.dto.Order;
import ru.ylab.hw.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the {@link OrderRepository} interface for managing orders in the database.
 */
public interface OrderRepository {

    /**
     * Saves the specified order to the database.
     * If an error occurs during the operation, the transaction is rolled back.
     *
     * @param order the order to be saved
     */
    void save(Order order);

    /**
     * Updates the status of an order with the specified id.
     * If an error occurs during the operation, the transaction is rolled back.
     *
     * @param id             the UUID of the order to be updated
     * @param orderNewStatus the new status to be set
     */
    void edit(UUID id, OrderStatus orderNewStatus);

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders
     */
    List<Order> findAll();

    /**
     * Finds an order by its id.
     *
     * @param id the UUID of the order to be found
     * @return an Optional containing the order if found, or empty if not found
     */
    Optional<Order> findById(UUID id);
}
