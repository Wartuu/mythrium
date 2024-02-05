package xyz.mythrium.backend.repository.account;

import xyz.mythrium.backend.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE lower(a.email) = lower(:email)")
    Account findByEmail(@Param("email") String email);

    @Query("SELECT a FROM Account a WHERE lower(a.username) = lower(:username)")
    Account findByUsername(@Param("username") String username);
}
