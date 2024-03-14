package xyz.mythrium.backend.config;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import xyz.mythrium.backend.service.NotificationService;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private NotificationService notificationService;

    @Autowired
    public WebsocketConfig(NotificationService notificationService) {
        this.notificationService = notificationService;
    }



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationService, "/ws/v1/notification").setAllowedOrigins("*").setAllowedOriginPatterns("*");
    }


}
