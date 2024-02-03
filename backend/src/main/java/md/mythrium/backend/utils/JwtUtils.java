package md.mythrium.backend.utils;

import io.jsonwebtoken.Jwts;
import md.mythrium.backend.entity.account.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class JwtUtils {
    private final long EXPIRATION = TimeUnit.DAYS.toMillis(7);

    @Value("${jwt.secret}")
    private String secret;


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
                .compact();
    }

    public boolean authenticateJwt(String jwt) {
        return false;
    }

}
