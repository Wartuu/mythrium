package xyz.mythrium.backend.config;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import xyz.mythrium.backend.service.NotificationService;
import xyz.mythrium.backend.service.bot.WorkerService;
import xyz.mythrium.backend.service.stream.StreamService;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final NotificationService notificationService;
    private final WorkerService workerService;
    private final StreamService streamService;


    @Autowired
    public WebsocketConfig(NotificationService notificationService, WorkerService workerService, StreamService streamService) {
        this.notificationService = notificationService;
        this.workerService = workerService;
        this.streamService = streamService;
    }



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationService, "/ws/v1/notification").setAllowedOrigins("*").setAllowedOriginPatterns("*");
        registry.addHandler(workerService, "/ws/v1/worker").setAllowedOrigins("*").setAllowedOriginPatterns("*");
        registry.addHandler(streamService, "/ws/v1/stream").setAllowedOrigins("*").setAllowedOriginPatterns("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(99999999);
        container.setMaxTextMessageBufferSize(99999999);
        return container;
    }




}
