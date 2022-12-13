package ru.kata.spring.boot_security.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

@Service
public class DTOServiceImpl implements DTOService {
    @Override
    public User convertDTOToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public UserDTO convertUserToDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
}
