package xyz.mythrium.backend.component.proxy;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "proxy")
public class ProxyConfig {
    private boolean enabled = false;
    private double usageLimit = 50;
    private int port = 7000;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(double usageLimit) {
        this.usageLimit = usageLimit;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
