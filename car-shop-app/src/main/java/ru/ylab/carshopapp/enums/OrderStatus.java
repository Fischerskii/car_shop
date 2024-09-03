package ru.ylab.carshopapp.enums;

/**
 * Enum representing the various statuses that an order can have
 * in the car dealership management system.
 */
public enum OrderStatus {
    /**
     * The order has been created and is awaiting further processing.
     */
    PENDING,

    /**
     * The order has been completed successfully.
     */
    COMPLETED,

    /**
     * The order has been cancelled and will not be processed further.
     */
    CANCELLED
}