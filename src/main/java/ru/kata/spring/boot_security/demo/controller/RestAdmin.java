package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rs/admin")
public class RestAdmin {

    private final UserService userService;
    private final RoleService roleService;

    public RestAdmin(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<UserDTO> showAdminPage() {
        return userService.getAllUsers().stream().distinct().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/authUser")
    public UserDTO authenticatedUser(Principal principal) {
        return convertToUserDTO((User) userService.loadUserByUsername(principal.getName()));
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/edit/{id}")
    public UserDTO edit(@PathVariable("id") int id) {
        return convertToUserDTO(userService.getUserById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO
        }
        User newUser = convertToUser(userDTO);
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
        if (bindingResult.hasErrors()){
            //TODO
        }
        userDTO.setId(id);
        User user = convertToUser(userDTO);
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }


}
