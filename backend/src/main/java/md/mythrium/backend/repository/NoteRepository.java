package md.mythrium.backend.repository;

import md.mythrium.backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.url = :url")
    Note findByUrl(String url);
}
