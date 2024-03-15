package xyz.mythrium.backend.entity.account;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ordinal", nullable = false)
    private AccountRole ordinal;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_visible", nullable = false)
    private boolean visibility;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Account> accounts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountRole getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(AccountRole ordinal) {
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }


    public Role(){}
    public Role(AccountRole ordinal, String name, boolean visibility) {
        this.ordinal = ordinal;
        this.name = name;
        this.visibility = visibility;
    }


}
