package xyz.mythrium.backend.entity.account;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum AccountRole {


    PRIVILEGES_ADMIN(false, "privileges: admin"),
    PRIVILEGES_USER(false, "privileges: user"),


    USER(true, "user"),
    MODERATOR(true, "moderator"),

    BOT(true, "bot"),
    PREMIUM(true, "premium account"),
    BETA_TESTER(true, "beta tester");


    final boolean visibility;
    final String displayName;

    private AccountRole(final boolean visibility, final String displayName) {
        this.visibility = visibility;
        this.displayName = displayName;
    }

    public boolean isVisible() {
        return visibility;
    }

    public String getDisplayName() {
        return displayName;
    }
}
