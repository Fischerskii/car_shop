package ru.ylab.carshopapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.ylab.carshopapp.enums.Role;

@Data
@AllArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    private Role role;
}
