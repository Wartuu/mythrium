package xyz.mythrium.backend.controller.api;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.entity.account.AccountRole;
import xyz.mythrium.backend.entity.account.Role;
import xyz.mythrium.backend.json.input.account.LoginInput;
import xyz.mythrium.backend.json.input.account.RegisterInput;
import xyz.mythrium.backend.json.output.ApiOutput;
import xyz.mythrium.backend.json.output.account.LoginOutput;
import xyz.mythrium.backend.json.output.account.OtpGenerationOutput;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.session.OAuthService;
import xyz.mythrium.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.bind.annotation.*;
import xyz.mythrium.backend.service.security.OtpService;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Set;
import java.util.regex.Pattern;


@Tag(
        name = "Account",
        description = "Account and session management"
)
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final RoleService roleService;
    private final OAuthService oAuthService;
    private final OtpService otpService;

    @Autowired
    public AccountController(AccountService accountService, RoleService roleService, OAuthService oAuthService, OtpService otpService) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.oAuthService = oAuthService;
        this.otpService = otpService;
    }



    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[A-Z])(?=.*[a-zA-Z]*[0-9])(?=.*[a-zA-Z]).{6,}");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9]{5,}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @GetMapping
    public ResponseEntity<ApiOutput> getAccountInformation(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String jwt) {
        if(jwt == null)
            return new ResponseEntity<>(new ApiOutput(false, "not valid session"), HttpStatus.OK);

        // TODO:

        return new ResponseEntity<>(new ApiOutput(false, "TODO"), HttpStatus.OK);
    }

    @PostMapping("/security/otp/new")
    public ResponseEntity<ApiOutput> generateOtpKey(@CookieValue("session") String jwt) {
        Account session = oAuthService.authenticateJWT(jwt);

        if(session == null)
            return new ResponseEntity<>(new ApiOutput(false, "not valid session"), HttpStatus.UNAUTHORIZED);


        String key = otpService.generateSecret(16);
        session.setOtpEnabled(false);
        session.setOtpKey(key);

        MultiFormatWriter writer = new MultiFormatWriter();


        String otpauthUrl = "otpauth://totp/" + session.getUsername() + "?secret=" + key + "&issuer=Mythrium";
        System.out.println(otpauthUrl);
        String encodedImage;

        try {
            BitMatrix matrix = writer.encode(otpauthUrl, BarcodeFormat.QR_CODE, 150, 150);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(MatrixToImageWriter.toBufferedImage(matrix), "png", bos);
            byte[] image = bos.toByteArray();
            encodedImage = Base64.getEncoder().encodeToString(image);

            accountService.save(session);
        } catch (WriterException | IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ApiOutput(false, "internal server error"), HttpStatus.UNAUTHORIZED);
        }



        return new ResponseEntity<>(new OtpGenerationOutput(true, "verification required", key, "data:image/png;base64," + encodedImage),HttpStatus.OK);
    }

    @PostMapping("/security/otp/confirm")
    public ResponseEntity<ApiOutput> verifyOtpKey(@RequestBody String auth, @CookieValue("session") String jwt) {
        System.out.println("executing...");
        Account session = oAuthService.authenticateJWT(jwt);

        if(session == null)
            return new ResponseEntity<>(new ApiOutput(false, "not valid session"), HttpStatus.UNAUTHORIZED);

        String key = session.getOtpKey();

        if(key == null)
            return new ResponseEntity<>(new ApiOutput(false, "you need to create key first"), HttpStatus.BAD_REQUEST);
        String code = otpService.generateTOTP(key);
        if(auth.length() == 6 && code.equals(auth)) {

            session.setOtpEnabled(true);
            accountService.save(session);
            return new ResponseEntity<>(new ApiOutput(true, "OTP (TOTP) verification enabled"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiOutput(false, "wrong code providen"), HttpStatus.BAD_REQUEST);
    }


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

        Account usernameAccount = accountService.getAccountByUsername(input.username);

        if(usernameAccount != null) {
            ApiOutput error = new ApiOutput(false, "username is taken");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Account emailAccount = accountService.getAccountByEmail(input.email);

        if(emailAccount != null) {
            ApiOutput error = new ApiOutput(false, "email is already used");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Account account = new Account();

        Role user = roleService.getRoleByOrdinal(AccountRole.USER);

        account.setRoles(Set.of(user));


        account.setEmail(input.email);
        account.setUsername(input.username);

        String salt = KeyGenerators.string().generateKey();

        String password = new BCryptPasswordEncoder().encode(salt + input.password);


        account.setPassword(salt + ":" + password); // TODO: SHA-256 / SHA-512 passwords

        account.setCreationDate(Calendar.getInstance().getTime());
        account.setOtpEnabled(false);

        accountService.addAccount(account);
        account = accountService.getAccountByEmail(account.getEmail());

        user.getAccounts().add(account);

        roleService.save(user);

        return new ResponseEntity<>(
                new ApiOutput(true, "success"),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiOutput> loginAccount(@RequestBody LoginInput input) {
        ApiOutput error = new ApiOutput(false, "wrong username or password");

        Account account = accountService.getAccountByUsername(input.username);

        if(account == null)
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);



        String salt = account.getPassword().split(":")[0];
        String passwordHash = account.getPassword().split(":")[1];


        boolean matches = new BCryptPasswordEncoder().matches(salt + input.password, passwordHash);

        if(!matches)
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

        String jwt = oAuthService.createJWT(account);

        LoginOutput loginOutput = new LoginOutput(true, "logged in", jwt);
        return new ResponseEntity<>(loginOutput, HttpStatus.OK);
    }

}
