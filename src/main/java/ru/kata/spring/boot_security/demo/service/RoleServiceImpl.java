package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }

    @Override
    public List<String> getListOfNamesUserRoles(User user) {
        return user.getRoles().stream()
                .map(x -> x.getRole().substring(5))
                .sorted().collect(Collectors.toList());
    }

    @Override
    public Set<Role> loadRolesToNewUser(User user) {
        List<Long> rolesId = user.getRoles().stream()
                .map(r -> r.getId()).collect(Collectors.toList());
        Set<Role> rolesLoaded = new HashSet<>();
        for (Long roleHash : rolesId) {
            rolesLoaded.add(getRoleById(roleHash));
        }
        return rolesLoaded;
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
