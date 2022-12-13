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
        User admin = new User("admin", "admin"
                , "Stephen King", (byte) 74, "stking@gmail.com");

        // login: user; pass: user
        User user = new User("user", "user"
                , "Jason Voorhees", (byte) 40, "jason@gmail.com");

        admin.setRoles(adminRoles);
        user.setRoles(userRoles);

        userService.saveUser(admin);
        userService.saveUser(user);

    }
}
