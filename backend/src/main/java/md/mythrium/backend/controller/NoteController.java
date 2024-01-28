package md.mythrium.backend.controller;


import md.mythrium.backend.json.input.NoteInput;
import md.mythrium.backend.json.output.ApiOutput;
import md.mythrium.backend.json.output.NoteOutput;
import md.mythrium.backend.service.AccountService;
import md.mythrium.backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        NoteOutput output = new NoteOutput(true, "success", uuid);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApiOutput> uploadNote(@RequestBody NoteInput input) {

    }

}
