package md.mythrium.backend.repository.account;

import md.mythrium.backend.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.sessionToken = :token")
    User findByToken(String token);
}
