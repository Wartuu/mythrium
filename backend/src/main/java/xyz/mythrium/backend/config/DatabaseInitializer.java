package xyz.mythrium.backend.config;


import jakarta.annotation.PostConstruct;
import xyz.mythrium.backend.entity.account.AccountRole;
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

        // ! WARNING: MODIFYING ORDER OF ACCOUNT ROLES CAN RESULT INTO UNWANTED PRIVILEGE ESCALATION FOR UNWANTED USERS

        roleService.syncRole(AccountRole.USER);
        roleService.syncRole(AccountRole.MODERATOR);
        roleService.syncRole(AccountRole.ADMIN);

        roleService.syncRole(AccountRole.BOT);
        roleService.syncRole(AccountRole.PREMIUM);
        roleService.syncRole(AccountRole.BETA_TESTER);
    }
}
