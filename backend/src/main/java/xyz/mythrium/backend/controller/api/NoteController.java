package xyz.mythrium.backend.controller.api;


import org.springframework.http.HttpStatus;
import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.entity.media.Note;
import xyz.mythrium.backend.json.input.media.NoteUploadInput;
import xyz.mythrium.backend.json.output.ApiOutput;
import xyz.mythrium.backend.json.output.CommonResponse;
import xyz.mythrium.backend.json.output.media.NoteContentOutput;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mythrium.backend.service.session.OAuthService;

import java.util.Calendar;
import java.util.Optional;
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

    @PostMapping("/upload")
    public ResponseEntity<ApiOutput> upload(@RequestBody NoteUploadInput input, @CookieValue("session") String session) {
        Account account = oAuthService.authenticateJWT(session);

        if(account == null)
            return CommonResponse.INVALID_SESSION;

        if(input.content.length() > 8192 || input.title.length() > 255)
            return CommonResponse.TOO_LARGE_INPUT;


        Note note = new Note();

        String uuid = UUID.randomUUID().toString();

        note.setAuthorId(account.getId());
        note.setCreationDate(Calendar.getInstance().getTime());
        note.setUuid(uuid);
        note.setViewCount(0);

        note.setContent(input.content);
        note.setPrivate(input.isPrivate);
        note.setTitle(input.title);

        noteService.addNote(note);

        return new ResponseEntity<ApiOutput>(new ApiOutput(true, uuid), HttpStatus.OK);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<ApiOutput> get(@PathVariable("uuid") String uuid, @CookieValue("session") String session) {
        Note note = noteService.getNoteByUUID(uuid);

        if(note == null)
            return CommonResponse.RESOURCE_NOT_FOUND;

        Optional<Account> author = accountService.getAccountById(note.getId());

        if(author.isEmpty())
            return new ResponseEntity<>(new ApiOutput(false, "internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);

        NoteContentOutput output = new NoteContentOutput(note, author.get().getUsername());


        if(!note.isPrivate())
            return new ResponseEntity<>(output, HttpStatus.OK);

        Account sessionAccount = oAuthService.authenticateJWT(session);

        if(sessionAccount == null)
            return CommonResponse.RESOURCE_NOT_FOUND; // faking not existing note (security reasons)

        if(sessionAccount.getId().equals(author.get().getId()))
            return new ResponseEntity<>(output, HttpStatus.OK);
        else
            return CommonResponse.RESOURCE_NOT_FOUND; // faking not existing note
    }

    // find by title phrase
    @GetMapping("/search/{phrase}")
    public ResponseEntity<ApiOutput> searchByKeywords(@PathVariable("phrase") String phrase) {
        return null;
    }





}
