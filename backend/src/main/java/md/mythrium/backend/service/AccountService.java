package md.mythrium.backend.service;


import md.mythrium.backend.entity.account.Account;
import md.mythrium.backend.entity.account.Role;
import md.mythrium.backend.repository.account.RoleRepository;
import md.mythrium.backend.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Account getBySession(String jwt) {
        return accountRepository.findByToken(jwt);
    }

    public void addUser(Account account) {
        accountRepository.saveAndFlush(account);
    }
}
