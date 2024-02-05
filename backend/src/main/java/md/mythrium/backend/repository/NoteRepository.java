package md.mythrium.backend.repository;

import md.mythrium.backend.entity.media.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.uuid = :uuid")
    Note findByUUID(@Param("uuid") String uuid);


    @Modifying
    @Transactional
    @Query("UPDATE Note n SET n.viewCount = n.viewCount + 1 WHERE n.uuid = :uuid")
    void updateViewCount(@Param("uuid") String uuid);
}
