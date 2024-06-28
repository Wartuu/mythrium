package xyz.mythrium.backend.service.bot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xyz.mythrium.backend.service.session.OAuthService;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorkerService extends TextWebSocketHandler {

    private final OAuthService oAuthService;
    private Map<String, String> sessionAccountMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(WorkerService.class);

    public WorkerService(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        logger.info("connection");

        session.sendMessage(new TextMessage("example message!"));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        logger.info(message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
