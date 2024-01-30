package md.mythrium.backend.repository.account;

import md.mythrium.backend.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.sessionToken = :token")
    Account findByToken(String token);
}
