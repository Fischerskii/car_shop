package ru.ylab.carshopapp.enums;

/**
 * Enum representing the different roles that users can have
 * in the car dealership management system.
 */
public enum Role {
    /**
     * Role with the highest level of access, responsible for managing the entire system.
     */
    ADMIN,

    /**
     * Role responsible for managing day-to-day operations, such as handling car sales and services.
     */
    MANAGER,

    /**
     * Role with the least access, representing a customer who can view and purchase cars.
     */
    CLIENT;
}
