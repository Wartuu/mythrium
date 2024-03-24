package xyz.mythrium.backend.service.session;

import org.springframework.stereotype.Service;

@Service
public class FingerprintService {


    public String generateFingerprint(String userAgent, String ip) {
        String data = userAgent + ip;

        return null;
    }
}
