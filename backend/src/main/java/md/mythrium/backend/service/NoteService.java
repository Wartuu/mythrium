package md.mythrium.backend.service;


import md.mythrium.backend.entity.media.Note;
import md.mythrium.backend.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(final NoteRepository repository) {
        this.repository = repository;
    }

    public Optional<Note> getNoteById(long id) {
        return repository.findById(id);
    }
    public Note getNoteByUUID(String uuid) {return repository.findByUUID(uuid);}
    public List<Note> getAllNotes() {
        return repository.findAll();
    }
    public void addNote(Note note) {
        repository.save(note);
    }

    @Transactional
    public void updateViewCounter(String uuid) {repository.updateViewCount(uuid);};
}
