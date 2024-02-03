package md.mythrium.backend.repository.account;

import md.mythrium.backend.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE lower(a.email) = lower(:email)")
    Account findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE lower(a.username) = lower(:username)")
    Account findByUsername(String username);
}
