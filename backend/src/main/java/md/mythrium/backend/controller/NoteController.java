package md.mythrium.backend.controller;


import md.mythrium.backend.json.ApiError;
import md.mythrium.backend.model.Note;
import md.mythrium.backend.repository.NoteRepository;
import md.mythrium.backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/note/")
public class NoteController {


    @Autowired
    private NoteService noteService;


    @GetMapping("/{noteUrl}")
    public Object getNote(@PathVariable String noteUrl) {
        if(noteUrl == null || noteUrl.equals("*"))
            return noteService.getAllNotes();

        Note note = noteService.getNoteByUrl(noteUrl);

        if(note != null)
            return note;

        return new ApiError(404, "url does not exist");

    }

}
