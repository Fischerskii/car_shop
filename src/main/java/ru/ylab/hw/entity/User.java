package ru.ylab.hw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ylab.hw.enums.Role;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Role role;
}
