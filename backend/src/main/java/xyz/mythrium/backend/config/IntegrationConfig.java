package xyz.mythrium.backend.config;


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

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public void setTwitch(String twitch) {
        this.twitch = twitch;
    }
}
