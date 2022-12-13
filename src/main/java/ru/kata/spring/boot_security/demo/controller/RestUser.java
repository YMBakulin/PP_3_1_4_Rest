package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.DTOService;

import java.security.Principal;

@RestController
@RequestMapping("/rs/user")
public class RestUser {

    private final DTOService dtoService;
    private final UserDetailsService userDetailsService;

    public RestUser(DTOService dtoService, UserDetailsService userDetailsService) {
        this.dtoService = dtoService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/authUser")
    public ResponseEntity<UserDTO> authUser(Principal principal) {
        return new ResponseEntity<>(dtoService.convertUserToDTO((User) userDetailsService
                .loadUserByUsername(principal.getName())), HttpStatus.OK);
    }

}
