package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.UserDTO;

import java.util.List;

public interface UserRepository {

    void save(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getAllUsers();

    void update(UserDTO userDTO);

    void delete(String username);
}
