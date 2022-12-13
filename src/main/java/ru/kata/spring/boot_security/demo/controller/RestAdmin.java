package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.DTOService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rs/admin")
public class RestAdmin {

    private final UserService userService;
    private final RoleService roleService;
    private final DTOService dtoService;

    private final UserDetailsService userDetailsService;

    public RestAdmin(UserService userService, RoleService roleService, DTOService dtoService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.dtoService = dtoService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> showAdminPage() {
        return new ResponseEntity<>(userService.getAllUsers().stream().distinct()
                .map(user -> dtoService.convertUserToDTO(user))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/authAdmin")
    public ResponseEntity<UserDTO> getAuthenticatedAdmin(Principal principal) {
        return new ResponseEntity<>(dtoService.convertUserToDTO((User) userDetailsService
                .loadUserByUsername(principal.getName())), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
        return new ResponseEntity<>(dtoService.convertUserToDTO(userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = dtoService.convertDTOToUser(userDTO);
        newUser.setRoles(roleService.loadRolesToNewUser(newUser));
        userService.saveUser(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userDTO.setId(id);
        User user = dtoService.convertDTOToUser(userDTO);
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
