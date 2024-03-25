package xyz.mythrium.backend.controller.api;


import xyz.mythrium.backend.entity.account.Account;
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
import xyz.mythrium.backend.service.session.OAuthService;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {


    private final NoteService noteService;
    private final AccountService accountService;
    private final OAuthService oAuthService;

    public NoteController(NoteService noteService, AccountService accountService, OAuthService oAuthService) {
        this.noteService = noteService;
        this.accountService = accountService;
        this.oAuthService = oAuthService;
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<ApiOutput> getNote(@PathVariable(value = "uuid") String uuid, @CookieValue(value = "session", required = false) String session) {
        if(uuid == null)
            return new ResponseEntity<>(new ApiOutput(false, "no uuid provided"), HttpStatus.OK);

        Note note = noteService.getNoteByUUID(uuid);

        if(note == null)
            return new ResponseEntity<>(new ApiOutput(false, "uuid does not exists"), HttpStatus.OK);

        if(!note.isPrivate()) {
            noteService.updateViewCounter(uuid);
            return new ResponseEntity<>(new NoteReadOutput(true, "success", note.getContent(), note.getViewCount()), HttpStatus.OK);
        }

        Account account = oAuthService.authenticateJWT(session);

        if(account == null)
            return new ResponseEntity<>(new ApiOutput(false, "invalid session, verification required"), HttpStatus.UNAUTHORIZED);

        if(Objects.equals(account.getId(), note.getAuthorId())) {
            noteService.updateViewCounter(uuid);
            return new ResponseEntity<>(new NoteReadOutput(true, "success", note.getContent(), note.getViewCount()), HttpStatus.OK);
        } else return new ResponseEntity<>(new ApiOutput(false, "you have no access to this resource"), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/")
    public ResponseEntity<ApiOutput> uploadNote(@RequestBody NoteInput input, @CookieValue("session") String session) {
        Account account = oAuthService.authenticateJWT(session);

        if(account == null)
            return new ResponseEntity<>(new ApiOutput(false, "invalid session"), HttpStatus.UNAUTHORIZED);

        String uuid = UUID.randomUUID().toString();

        Note note = new Note();
        note.setAuthorId(account.getId());
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
