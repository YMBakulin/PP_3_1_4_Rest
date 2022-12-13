package ru.kata.spring.boot_security.demo.dbinit;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {

    private final RoleService roleService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public DBInit(RoleService roleService, UserService userService, UserDetailsService userDetailsService) {
        this.roleService = roleService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    private void postConstruct() {

        User checkAdmin = (User) userDetailsService.loadUserByUsername("admin");
        User checkUser = (User) userDetailsService.loadUserByUsername("admin");
        if (checkAdmin != null || checkUser != null) {
            return;
        }

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        Set<Role> adminRoles = new HashSet<>();
        Set<Role> userRoles = new HashSet<>();

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

        adminRoles.add(roleAdmin);
        userRoles.add(roleUser);

        // login: admin; pass: admin
        User admin = new User("admin", "$2a$12$zeefW0k9MsLZkB9RroFd3uH3BjixlT.lDq4ka9JsnXz3m7Ni6wYay"
                , "Stephen King", (byte) 74, "stking@gmail.com");

        // login: user; pass: user
        User user = new User("user", "$2a$12$iS8bIBzagTLGrNp5DJ5SNOeaVMOzKJSKP5R9Kqc5QfVKysGckOiKq"
                , "Jason Voorhees", (byte) 40, "jason@gmail.com");

        admin.setRoles(adminRoles);
        user.setRoles(userRoles);

        userService.saveUser(admin);
        userService.saveUser(user);

    }
}
