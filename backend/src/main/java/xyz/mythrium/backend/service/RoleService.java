package xyz.mythrium.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.mythrium.backend.entity.account.AccountRole;
import xyz.mythrium.backend.entity.account.Role;
import xyz.mythrium.backend.repository.account.RoleRepository;

import java.util.List;

@Service
@CacheConfig(cacheNames = "roles")
public class RoleService {
    private RoleRepository roleRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Transactional
    @Cacheable
    public void syncRole(AccountRole accountRole) {
        // checking if exists

        Role role = roleRepository.findByOrdinal(accountRole);

        if(role == null) {
            roleRepository.addRole(accountRole, accountRole.getDisplayName(), accountRole.isVisible());
            return;
        }

        if(!role.getName().equals(accountRole.getDisplayName()))
            role.setName(accountRole.getDisplayName());


        if(role.isVisible() != accountRole.isVisible())
            role.setVisibility(accountRole.isVisible());

        roleRepository.save(role);
    }

    @Cacheable
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Cacheable
    public Role getRoleByOrdinal(AccountRole accountRole) {
        return roleRepository.findByOrdinal(accountRole);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

}
