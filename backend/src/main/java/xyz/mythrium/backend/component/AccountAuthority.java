package xyz.mythrium.backend.component;

import org.springframework.security.core.GrantedAuthority;

public class AccountAuthority implements GrantedAuthority {
    private String authority;

    public AccountAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
