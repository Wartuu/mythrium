package md.mythrium.backend.service;


import md.mythrium.backend.entity.account.Role;
import md.mythrium.backend.entity.account.User;
import md.mythrium.backend.repository.account.RoleRepository;
import md.mythrium.backend.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountService(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
