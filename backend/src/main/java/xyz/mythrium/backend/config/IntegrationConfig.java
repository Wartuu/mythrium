package xyz.mythrium.backend.component;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "integration")
public class IntegrationConfig {
    private String discord;
    private String twitch;

    public String getDiscord() {
        return discord;
    }

    public String getTwitch() {
        return twitch;
    }
}
