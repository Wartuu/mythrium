package xyz.mythrium.backend.component.proxy;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "proxy")
public class ProxyConfig {
    private boolean enabled = false;
    private int dailyLimit = 1000;
    private int port = 7000;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
