package xyz.mythrium.backend.config;


import jakarta.annotation.PostConstruct;
import xyz.mythrium.backend.entity.account.AccountRole;
import xyz.mythrium.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    private final AccountService accountService;

    public DatabaseInitializer(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    private void loadRoles() {
        accountService.syncRole(AccountRole.PRIVILEGES_ADMIN);
        accountService.syncRole(AccountRole.PRIVILEGES_USER);

        accountService.syncRole(AccountRole.USER);
        accountService.syncRole(AccountRole.MODERATOR);

        accountService.syncRole(AccountRole.BOT);
        accountService.syncRole(AccountRole.PREMIUM);
        accountService.syncRole(AccountRole.BETA_TESTER);
    }
}
