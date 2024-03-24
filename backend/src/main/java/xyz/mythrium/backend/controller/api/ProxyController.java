package xyz.mythrium.backend.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mythrium.backend.component.proxy.ProxyServer;

@RestController
@RequestMapping("/api/v1/proxy")
public class ProxyController {

    @Autowired
    ProxyServer server;

    @GetMapping
    public String proxy() {
        return server.getConnectionsMade() + " | running:  " + server.isRunning();
    }

}
