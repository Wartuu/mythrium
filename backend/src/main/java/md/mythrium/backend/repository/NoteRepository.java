package md.mythrium.backend.repository;

import md.mythrium.backend.entity.media.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
