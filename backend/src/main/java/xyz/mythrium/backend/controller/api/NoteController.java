package xyz.mythrium.backend.controller.api;


import xyz.mythrium.backend.entity.media.Note;
import xyz.mythrium.backend.json.input.NoteInput;
import xyz.mythrium.backend.json.output.ApiOutput;
import xyz.mythrium.backend.json.output.NoteReadOutput;
import xyz.mythrium.backend.json.output.NoteWriteOutput;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class NoteController {


    @Autowired
    private NoteService noteService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/note/{uuid}")
    public ResponseEntity<ApiOutput> getNote(@PathVariable(value = "uuid") String uuid) {
        if(uuid == null)
            return new ResponseEntity<>(new ApiOutput(false, "no uuid provided"), HttpStatus.OK);

        Note note = noteService.getNoteByUUID(uuid);

        noteService.updateViewCounter(uuid);

        if(note == null)
            return new ResponseEntity<>(new ApiOutput(false, "uuid does not exists"), HttpStatus.OK);


        return new ResponseEntity<>(new NoteReadOutput(true, "success", note.getContent(), note.getViewCount()), HttpStatus.OK);
    }

    @PostMapping("/note")
    public ResponseEntity<ApiOutput> uploadNote(@RequestBody NoteInput input, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String jwt) {

        if(jwt == null)
            return new ResponseEntity<>(new ApiOutput(false, "not valid session"), HttpStatus.OK);


//        User user = accountService.getBySession(jwt);
//
//        if(user == null) {
//            ApiOutput error = new ApiOutput(false, "not logged in");
//            return new ResponseEntity<>(error, HttpStatus.OK);
//        }

        String uuid = UUID.randomUUID().toString();

        Note note = new Note();
        note.setAuthor_id(0L);
        note.setUuid(uuid);
        note.setPassword(input.password);
        note.setPrivate(input.isPrivate);
        note.setBurnAfterRead(input.burnAfterRead);
        note.setExpirationDate(input.expirationDate);
        note.setCreationDate(Calendar.getInstance().getTime());
        note.setContent(input.content);
        note.setViewCount(0);

        noteService.addNote(note);

        NoteWriteOutput output = new NoteWriteOutput(true, "note created", uuid);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
