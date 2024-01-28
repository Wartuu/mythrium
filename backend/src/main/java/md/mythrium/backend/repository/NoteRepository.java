package md.mythrium.backend.repository;

import md.mythrium.backend.entity.media.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
