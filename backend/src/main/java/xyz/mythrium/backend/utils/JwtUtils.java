package xyz.mythrium.backend.utils;

import io.jsonwebtoken.Jwts;
import xyz.mythrium.backend.entity.account.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class JwtUtils {
    private final long EXPIRATION = TimeUnit.DAYS.toMillis(7);
    @Value("${jwt.secret}")
    private String secret;

    public JwtUtils() {

    }


    public String generateJwt(Account account) {
        // todo: add roles


        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claim("uuid", UUID.randomUUID().toString())
                .claim("exp", String.valueOf(System.currentTimeMillis() + EXPIRATION))
                .claim("iat", String.valueOf(System.currentTimeMillis()))

                .claim("email", account.getEmail())
                .claim("username", account.getUsername())
                //TODO: OAUTH
                .compact();
    }

    public boolean authenticateJwt(String jwt) {
        return false;
    }

}