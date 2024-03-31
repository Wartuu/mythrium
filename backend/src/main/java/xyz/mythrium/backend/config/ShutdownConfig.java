package xyz.mythrium.backend.config;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownConfig {

    @PreDestroy
    public void shutdownConfig() {

    }

}
