package xyz.mythrium.backend.service.security;


import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class OtpService {

    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private static final String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final String keyChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int DIGITS = 6;
    private static final int TIME_STEP_SECONDS = 30;


    public String generateSecret(int size) {
        SecureRandom secureRandom = new SecureRandom();

        StringBuilder builder = new StringBuilder();
        byte[] bytes = new byte[size];

        secureRandom.nextBytes(bytes);

        for (byte b : bytes) {
            int index = Math.abs(b) % base32Chars.length();
            builder.append(base32Chars.charAt(index));
        }


        return builder.toString();
    }

    public String generateTOTP(String secret) {
        try {
            secret = secret.toUpperCase();
            byte[] bytes = new byte[secret.length() * 5 / 8];
            int bytesWritten = 0;
            int buffer = 0;
            int bitsRemaining = 0;

            for (char c : secret.toCharArray()) {
                int value = base32Chars.indexOf(c);
                if (value < 0) continue;

                buffer <<= 5;
                buffer |= value;
                bitsRemaining += 5;
                if (bitsRemaining >= 8) {
                    bytes[bytesWritten++] = (byte) (buffer >> (bitsRemaining - 8));
                    bitsRemaining -= 8;
                }
            }


            byte[] msg = ByteBuffer.allocate(8).putLong(Instant.now().getEpochSecond() / TIME_STEP_SECONDS).array();

            SecretKeySpec keySpec = new SecretKeySpec(bytes, HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(keySpec);
            byte[] hash = mac.doFinal(msg);
            int offset = hash[hash.length - 1] & 0xF;
            int binary =
                    ((hash[offset] & 0x7f) << 24) |
                            ((hash[offset + 1] & 0xff) << 16) |
                            ((hash[offset + 2] & 0xff) << 8) |
                            (hash[offset + 3] & 0xff);
            int otp = binary % (int) Math.pow(10, DIGITS);
            return String.format("%0" + DIGITS + "d", otp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
