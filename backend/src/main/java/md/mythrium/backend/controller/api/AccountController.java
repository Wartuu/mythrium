package md.mythrium.backend.controller.api;


import md.mythrium.backend.config.ExceptionConfig;
import md.mythrium.backend.entity.account.Account;
import md.mythrium.backend.entity.account.Role;
import md.mythrium.backend.json.input.LoginInput;
import md.mythrium.backend.json.input.RegisterInput;
import md.mythrium.backend.json.output.ApiOutput;
import md.mythrium.backend.json.output.LoginOutput;
import md.mythrium.backend.repository.account.AccountRepository;
import md.mythrium.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {


    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final JwtUtils jwtUtils;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[A-Z])(?=.*[a-zA-Z]*[0-9])(?=.*[a-zA-Z]).{6,}");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9]{5,}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public AccountController(AccountRepository accountRepository, JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<ApiOutput> getAccountInformation(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String jwt) {
        if(jwt == null)
            return new ResponseEntity<>(new ApiOutput(false, "not valid session"), HttpStatus.OK);

        // TODO:

        return new ResponseEntity<>(new ApiOutput(false, "TODO"), HttpStatus.OK);
    }

    @GetMapping("/verify/{verificationId}")
    public String verifyMail(@PathVariable String verificationId) {
        return "ok";
    }

    //TODO: /account/manage


    @PostMapping("/register")
    public ResponseEntity<ApiOutput> createNewAccount(@RequestBody RegisterInput input) {

        if(!USERNAME_PATTERN.matcher(input.username).find()) {
            ApiOutput error = new ApiOutput(false, "username size needs to be 5+, cannot contain special characters");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if(!PASSWORD_PATTERN.matcher(input.password).find()) {
            ApiOutput error = new ApiOutput(false, "password size needs to be 6+, needs at least 1 uppercase letter and number");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if(!EMAIL_PATTERN.matcher(input.email).find()) {
            ApiOutput error = new ApiOutput(false, "invalid email");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Account usernameAccount = accountRepository.findByUsername(input.username);

        if(usernameAccount != null) {
            ApiOutput error = new ApiOutput(false, "username is taken");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Account emailAccount = accountRepository.findByEmail(input.email);

        if(emailAccount != null) {
            ApiOutput error = new ApiOutput(false, "email is already used");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Account account = new Account();




        Set<Role> defaultRoles = new HashSet<>();
        account.setRoles(defaultRoles);

        account.setEmail(input.email);
        account.setUsername(input.username);

        String salt = KeyGenerators.string().generateKey();

        String password = new BCryptPasswordEncoder().encode(salt + input.password);


        account.setPassword(salt + ":" + password); // TODO: SHA-256 / SHA-512 passwords

        account.setCreationDate(Calendar.getInstance().getTime());

        accountRepository.save(account);

        return new ResponseEntity<>(
                new ApiOutput(true, "success"),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiOutput> loginAccount(@RequestBody LoginInput input) {
        ApiOutput error = new ApiOutput(false, "wrong username or password");

        Account account = accountRepository.findByUsername(input.username);

        if(account == null)
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);



        String salt = account.getPassword().split(":")[0];
        String passwordHash = account.getPassword().split(":")[1];


        boolean matches = new BCryptPasswordEncoder().matches(salt + input.password, passwordHash);

        if(!matches)
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

        String jwt = jwtUtils.generateJwt(account);

        LoginOutput loginOutput = new LoginOutput(true, "logged in", jwt);
        return new ResponseEntity<>(loginOutput, HttpStatus.OK);
    }

}
