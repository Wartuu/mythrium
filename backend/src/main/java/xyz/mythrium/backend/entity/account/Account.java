package xyz.mythrium.backend.entity.account;


import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    // SHA-512 + SALTED password
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "otp_key", length = 16)
    private String otp_key;

    @Column(name = "otp_enabled", nullable = false)
    private boolean otp_enabled;

    @ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getOtpKey() {
        return otp_key;
    }

    public void setOtpKey(String otp_key) {
        this.otp_key = otp_key;
    }

    public boolean isOtpEnabled() {
        return otp_enabled;
    }

    public void setOtpEnabled(boolean otp_enabled) {
        this.otp_enabled = otp_enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
