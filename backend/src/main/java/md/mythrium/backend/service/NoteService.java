package md.mythrium.backend.service;


import md.mythrium.backend.model.Note;
import md.mythrium.backend.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(final NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    public Note getNoteByUrl(String url) {
        return repository.findByUrl(url);
    }
}
