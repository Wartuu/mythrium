package md.mythrium.backend.service;


import md.mythrium.backend.entity.Note;
import md.mythrium.backend.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(final NoteRepository repository) {
        this.repository = repository;
    }

}
