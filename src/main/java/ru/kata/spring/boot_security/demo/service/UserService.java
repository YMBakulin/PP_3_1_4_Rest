package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    void saveUser(User user);

    void updateUser(User user);

    void removeUserById(long id);

    String getStringOfRoles(User user);
}
