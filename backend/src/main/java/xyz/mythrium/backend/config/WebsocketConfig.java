package xyz.mythrium.backend.config;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import xyz.mythrium.backend.service.NotificationService;
import xyz.mythrium.backend.service.bot.WorkerService;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final NotificationService notificationService;
    private final WorkerService workerService;

    @Autowired
    public WebsocketConfig(NotificationService notificationService, WorkerService workerService) {
        this.notificationService = notificationService;
        this.workerService = workerService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationService, "/ws/v1/notification").setAllowedOrigins("*").setAllowedOriginPatterns("*");
        registry.addHandler(workerService, "/ws/v1/worker").setAllowedOrigins("*").setAllowedOriginPatterns("*");
    }


}
