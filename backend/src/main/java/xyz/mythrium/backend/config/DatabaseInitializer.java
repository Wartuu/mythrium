package xyz.mythrium.backend.config;


import jakarta.annotation.PostConstruct;
import xyz.mythrium.backend.entity.account.AccountRole;
import xyz.mythrium.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.mythrium.backend.service.RoleService;

@Component
public class DatabaseInitializer {
    @Autowired
    private final RoleService roleService;

    public DatabaseInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    private void loadRoles() {
        roleService.syncRole(AccountRole.PRIVILEGES_ADMIN);
        roleService.syncRole(AccountRole.PRIVILEGES_USER);

        roleService.syncRole(AccountRole.USER);
        roleService.syncRole(AccountRole.MODERATOR);

        roleService.syncRole(AccountRole.BOT);
        roleService.syncRole(AccountRole.PREMIUM);
        roleService.syncRole(AccountRole.BETA_TESTER);
    }
}
