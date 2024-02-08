package xyz.mythrium.backend.controller.ws;


import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;

@Controller("/ws/v1/notification")
public class NotificationController {

    @ConnectMapping
    public void connect() {

    }
}
