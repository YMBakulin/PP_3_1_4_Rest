package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        userRepository.deleteById(id);
        userRepository.flush();
    }

    @Override
    public String getStringOfRoles(User user) {
        StringBuilder str = new StringBuilder();
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            str.append(iterator.next().getRole().substring(5));
            if (iterator.hasNext()) {
                str.append(", ");
            }
        }
        return str.toString();
    }
}
