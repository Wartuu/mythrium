package md.mythrium.backend.repository.account;

import md.mythrium.backend.entity.account.AccountRole;
import md.mythrium.backend.entity.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(@Param("name") AccountRole name);

    @Modifying
    @Query("INSERT INTO Role (ordinal, name, visibility) VALUES (:ordinal, :name, :isVisible)")
    void addRole(@Param("ordinal") AccountRole ordinal, @Param("name") String name, @Param("isVisible") boolean isVisible);


    @Query("SELECT count(r) FROM Role r WHERE r.ordinal = :ordinal")
    int countByOrdinal(@Param("ordinal") AccountRole ordinal);


    @Query("SELECT r FROM Role r WHERE r.ordinal = :ordinal")
    Role findByOrdinal(@Param("ordinal") AccountRole ordinal);
}
