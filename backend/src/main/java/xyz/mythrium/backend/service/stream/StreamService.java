package xyz.mythrium.backend.service.stream;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StreamService extends BinaryWebSocketHandler {

    private List<WebSocketSession> viewers = new ArrayList<>();

    public StreamService() throws Exception {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connection");

        if(session.getUri().getQuery() == null) {
            System.out.println("no query data");
            session.close();
        }

        String[] params = session.getUri().getQuery().split("&");

        for (String param : params) {
            String key = param.split("=")[0];
            String value = param.split("=")[1];

            if(key.equals("uploader") && value.equals("false"))
                viewers.add(session);

        }

    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);


        for (WebSocketSession viewer : viewers) {
            viewer.sendMessage(message);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        viewers.remove(session);
    }
}
