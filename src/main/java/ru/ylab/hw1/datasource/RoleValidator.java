package ru.ylab.hw1.datasource;

import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.PermissionsException;

public record RoleValidator() {

    public void checkPermissionNotCustomer(Role role) throws PermissionsException {
        if (role == Role.CUSTOMER) {
            throw new PermissionsException("User doesn't have permissions to perform this operation");
        }
    }
}
