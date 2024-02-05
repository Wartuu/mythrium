package xyz.mythrium.backend.service;


import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.entity.account.AccountRole;
import xyz.mythrium.backend.entity.account.Role;
import xyz.mythrium.backend.repository.account.RoleRepository;
import xyz.mythrium.backend.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountService(final AccountRepository accountRepository, final RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Account getBySession(String jwt) {
        return null;
    }

    public void addUser(Account account) {
        accountRepository.saveAndFlush(account);
    }

    @Transactional
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
}
