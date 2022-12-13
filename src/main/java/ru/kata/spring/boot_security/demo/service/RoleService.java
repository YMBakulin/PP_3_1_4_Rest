package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(long id);

    List<String> getListOfNamesUserRoles(User user);

    Set<Role> loadRolesToNewUser(User user);

    void addRole(Role role);
}
