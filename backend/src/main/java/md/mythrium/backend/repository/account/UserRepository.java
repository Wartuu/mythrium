package md.mythrium.backend.repository.account;

import md.mythrium.backend.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
