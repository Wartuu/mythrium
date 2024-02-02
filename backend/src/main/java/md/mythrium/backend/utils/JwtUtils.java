package md.mythrium.backend.utils;

import io.jsonwebtoken.Jwts;
import md.mythrium.backend.entity.account.Account;
import md.mythrium.backend.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class JwtUtils {
    private final long EXPIRATION = TimeUnit.DAYS.toMillis(7);

    @Autowired
    private final SignatureService signatureService;


    public JwtUtils(SignatureService signatureService) {
        this.signatureService = signatureService;
    }


    public String generateJwt(Account account) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .content("uuid", UUID.randomUUID().toString())
                .content("exp", String.valueOf(System.currentTimeMillis() + EXPIRATION))
                .content("iat", String.valueOf(System.currentTimeMillis()))

                .content("email", account.getEmail())
                .content("username", account.getUsername())
                .claim("authority", account.getRoles())

                .signWith(signatureService.getSignature())
                .compact();
    }

    public boolean authenticateJwt(String jwt) {
        return false;
    }

}
