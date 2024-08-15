package ru.ylab.hw.enums;

/**
 * Enum representing the status of a service request
 * in the car dealership management system.
 */
public enum ServiceStatus {
    /**
     * The service request has been created and is awaiting processing.
     */
    PENDING,

    /**
     * The service request has been completed.
     */
    COMPLETED,

    /**
     * The service request has been cancelled and will not be processed.
     */
    CANCELLED
}
