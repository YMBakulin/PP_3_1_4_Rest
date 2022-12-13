package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

public interface DTOService {
    User convertDTOToUser(UserDTO userDTO);

    UserDTO convertUserToDTO(User user);
}
