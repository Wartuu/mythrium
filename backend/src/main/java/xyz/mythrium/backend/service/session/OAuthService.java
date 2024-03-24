package xyz.mythrium.backend.service.session;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.entity.account.Role;
import xyz.mythrium.backend.service.AccountService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OAuthService {
    private static final long EXPIRATION = TimeUnit.DAYS.toMillis(7);

    private static final int SECRET_SIZE = 32;
    private final SecretKey SECRET;

    private final AccountService accountService;


    @Autowired
    public OAuthService(AccountService accountService) {
        this.accountService = accountService;

        // if we want to use 2 servers we need to define single key

        SecureRandom secureRandom = new SecureRandom();

        byte[] randomBytes = new byte[SECRET_SIZE];
        secureRandom.nextBytes(randomBytes);

        SECRET = new SecretKeySpec(randomBytes, "HmacSHA256");
    }

    public String createJWT(Account account) {
        List<String> roles = new ArrayList<>();

        for (Role role : account.getRoles()) {
            roles.add(role.getName());
        }

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claim("uuid", UUID.randomUUID().toString())
                .claim("exp", String.valueOf((System.currentTimeMillis() + EXPIRATION) / 1000))
                .claim("iat", String.valueOf(System.currentTimeMillis() / 1000))

                .claim("email", account.getEmail())
                .claim("username", account.getUsername())
                .claim("roles", roles)
                .signWith(SECRET)
                .compact();
    }

    public Account authenticateJWT(String jwt) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(SECRET)
                    .build().parseSignedClaims(jwt);

            Claims claims = jws.getPayload();

            Date expiration = claims.getExpiration();

            if(expiration != null && expiration.before(new Date())) {
                return null;
            }

            String email = claims.get("email", String.class);
            String username = claims.get("username", String.class);

            Account account = accountService.getAccountByEmail(email);

            if(account.getUsername().equalsIgnoreCase(claims.get("username", String.class))) {
                return account;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }


    }


}
