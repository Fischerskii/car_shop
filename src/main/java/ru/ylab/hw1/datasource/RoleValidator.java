package ru.ylab.hw1.datasource;

import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.exceptions.PermissionsException;

public class RoleValidator {

    public final Role role;

    public RoleValidator(Role role) {
        this.role = role;
    }

    public void checkPermissionNotCustomer() throws PermissionsException {
        if (role == Role.CUSTOMER) {
            throw new PermissionsException("User doesn't have permissions to perform this operation");
        }
    }
}
