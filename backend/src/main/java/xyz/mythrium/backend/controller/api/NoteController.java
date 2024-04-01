package xyz.mythrium.backend.controller.api;


import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.json.output.ApiOutput;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mythrium.backend.service.session.OAuthService;

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
    public ResponseEntity<ApiOutput> upload(@RequestBody String input, @CookieValue("session") String session) {
        Account account = oAuthService.authenticateJWT(session);
        return null;
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<ApiOutput> get(@PathVariable("uuid") String uuid) {
        return null;
    }

    @PostMapping("/search")
    public ResponseEntity<ApiOutput> searchByKeywords() {
        return null;
    }





}
