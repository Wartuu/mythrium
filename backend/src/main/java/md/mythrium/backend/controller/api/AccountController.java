package md.mythrium.backend.controller.api;


import md.mythrium.backend.entity.account.Account;
import md.mythrium.backend.entity.account.Role;
import md.mythrium.backend.json.input.RegisterInput;
import md.mythrium.backend.json.output.ApiOutput;
import md.mythrium.backend.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {


    @Autowired
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public ResponseEntity<ApiOutput> getAccountInformation(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String jwt) {
        if(jwt == null)
            return new ResponseEntity<>(new ApiOutput(false, "not valid session"), HttpStatus.OK);

        // TODO:

        return new ResponseEntity<>(new ApiOutput(false, "TODO"), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ApiOutput> createNewAccount(@RequestBody RegisterInput input) {
        Account account = new Account();

        account.setLastLogin(null);

        Set<Role> defaultRoles = new HashSet<>();
        account.setRoles(defaultRoles);

        account.setEmail(input.email);
        account.setUsername(input.username);
        account.setPassword(input.password); // TODO: SHA-256 / SHA-512 passwords

        account.setCreationDate(Calendar.getInstance().getTime());
        account.setLastLogin(Calendar.getInstance().getTime());
        account.setApiToken(null);
        account.setSessionToken(null);


        return null;

    }

}
