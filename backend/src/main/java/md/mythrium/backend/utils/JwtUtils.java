package md.mythrium.backend.utils;

import io.jsonwebtoken.Jwts;

import java.util.concurrent.TimeUnit;

public class JwtUtils {
    private static final String SIGNATURE = "TEST_SIGNATURE";
    private static final long EXPIRATION = TimeUnit.DAYS.toMillis(14);

    public static String generateJwt(String data) {
        return null;
    }

}
