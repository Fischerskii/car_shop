package ru.ylab.hw1.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.repository.UserRepository;
import ru.ylab.hw1.service.UserService;
import ru.ylab.hw1.dto.UserDTO;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String username, String password, Role role) {
        userRepository.save(new UserDTO(username, password, role));
        log.info("User with name {} registered", username);
    }

    public UserDTO login(String username, String password) {
        UserDTO userDTO = userRepository.getUserByUsername(username);
        log.info("User logged in: {}", username);
        return (userDTO != null && userDTO.getPassword().equals(password)) ? userDTO : null;
    }

    public List<UserDTO> viewUsers() {
        return userRepository.getAllUsers();
    }
}
