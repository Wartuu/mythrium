package md.mythrium.backend.controller.api;


import md.mythrium.backend.entity.account.User;
import md.mythrium.backend.entity.media.Note;
import md.mythrium.backend.json.input.NoteInput;
import md.mythrium.backend.json.output.ApiOutput;
import md.mythrium.backend.json.output.NoteReadOutput;
import md.mythrium.backend.json.output.NoteWriteOutput;
import md.mythrium.backend.service.AccountService;
import md.mythrium.backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/note")
public class NoteController {


    @Autowired
    private NoteService noteService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/{uuid}")
    public ResponseEntity<ApiOutput> getNote(@PathVariable(value = "uuid") String uuid) {
        if(uuid == null)
            return new ResponseEntity<>(new ApiOutput(false, "no uuid provided"), HttpStatus.OK);

        NoteReadOutput output = new NoteReadOutput(true, "success", uuid);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApiOutput> uploadNote(@RequestBody NoteInput input, @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        System.out.println(jwt);

        User user = accountService.getBySession(jwt);

        if(user == null) {
            ApiOutput error = new ApiOutput(false, "not logged in");
            return new ResponseEntity<>(error, HttpStatus.OK);
        }

        String uuid = UUID.randomUUID().toString();

        Note note = new Note();
        note.setAuthor_id(user.getId());
        note.setUuid(uuid);
        note.setPassword(input.password);
        note.setPrivate(input.isPrivate);
        note.setBurnAfterRead(input.burnAfterRead);
        note.setExpirationDate(input.expirationDate);
        note.setCreationDate(Calendar.getInstance().getTime());
        note.setView_count(0);

        noteService.addNote(note);

        NoteWriteOutput output = new NoteWriteOutput(true, "note created", uuid);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
