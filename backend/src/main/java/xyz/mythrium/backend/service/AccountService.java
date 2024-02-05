package xyz.mythrium.backend.service;


import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.repository.account.RoleRepository;
import xyz.mythrium.backend.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

    public Account getBySession(String jwt) {
        return null;
    }

    public void addAccount(Account account) {
        accountRepository.saveAndFlush(account);
    }


}
