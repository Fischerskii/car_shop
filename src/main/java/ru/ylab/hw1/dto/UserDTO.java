package ru.ylab.hw1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ylab.hw1.enums.Role;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private Role role;
}
