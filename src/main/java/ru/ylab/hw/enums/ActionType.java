package ru.ylab.hw.enums;

/**
 * Enum representing different types of actions that can be logged in the application.
 * These actions correspond to significant user activities within the car dealership management system.
 */
public enum ActionType {
    /**
     * Represents the action of registering a new user in the system.
     */
    REGISTER,

    /**
     * Represents the action of logging into the system.
     */
    LOGIN,

    /**
     * Represents the action of adding a new car to the inventory.
     */
    ADD_CAR,

    /**
     * Represents the action of editing the details of an existing car in the inventory.
     */
    EDIT_CAR,

    /**
     * Represents the action of deleting a car from the inventory.
     */
    DELETE_CAR,

    /**
     * Represents the action of creating a new order for a car.
     */
    CREATE_ORDER,

    /**
     * Represents the action of changing the status of an existing order.
     */
    CHANGE_ORDER_STATUS,

    /**
     * Represents the action of creating a new service request for a car.
     */
    CREATE_SERVICE_REQUEST,

    /**
     * Represents the action of changing the status of an existing service request.
     */
    CHANGE_SERVICE_REQUEST_STATUS
}

