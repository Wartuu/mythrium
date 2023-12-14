package md.mythrium.backend.controller;


import md.mythrium.backend.json.ApiError;
import md.mythrium.backend.entity.Note;
import md.mythrium.backend.service.AccountService;
import md.mythrium.backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/")
public class NoteController {


    @Autowired
    private NoteService noteService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/note")
    public Object getNote(@RequestParam(value = "uuid", required = false) String uuid) {
        accountService.getAllRoles();
        accountService.getAllUsers();

        return "";
    }

}
