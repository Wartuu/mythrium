package xyz.mythrium.backend.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mythrium.backend.component.proxy.ProxyConfig;
import xyz.mythrium.backend.component.proxy.ProxyServer;
import xyz.mythrium.backend.json.output.ProxyInfoOutput;

@RestController
@RequestMapping("/api/v1/proxy")
public class ProxyController {

    private final ProxyServer server;
    private final ProxyConfig config;


    @Autowired
    public ProxyController(ProxyServer server, ProxyConfig config) {
        this.server = server;
        this.config = config;
    }

    @GetMapping
    public ProxyInfoOutput proxy() {
        return new ProxyInfoOutput(
                true,
                "fetched data",
                (double) server.getTotalBytesTransferred() / 1_048_576,
                server.getBytesPerSecond() / 1024,
                server.isRunning(),
                server.getConnectionsMade(),
                config.getDailyLimit() - server.getConnectionsMade()
        );
    }

}
