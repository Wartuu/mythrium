package xyz.mythrium.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.json.input.message.NotificationMessage;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class NotificationService extends TextWebSocketHandler {

    private AccountService accountService;
    private Map<Account, WebSocketSession> sessions = new HashMap<>();

    @Autowired
    public NotificationService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void sendNotification(NotificationMessage message, Account account) {
        String json = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(message);

            WebSocketSession session = sessions.get(account);
            session.sendMessage(new TextMessage(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendGlobalNotification(NotificationMessage message) {
        String json;

        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(message);
            TextMessage notification = new TextMessage(json);

            sessions.forEach(
                    (key, value) -> {
                        try {
                            value.sendMessage(notification);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String authorization = session.getHandshakeHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(!authorization.startsWith("Bearer ")) {
            session.close();
            return;
        }

        String jwt = authorization.split(" ")[1];

        // authorize jwt, and create accounts

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
    }
}
