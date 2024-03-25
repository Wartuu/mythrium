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
        double totalTransfer = (double) server.getTotalBytesTransferred() / 1_048_576;
        double kilobytesPerSecond = server.getBytesPerSecond() / 1024;
        double megabytesPerMinute = server.getBytesPerMinute() / 1_048_576;

        totalTransfer = (double) Math.round(totalTransfer * 1000) / 1000;
        kilobytesPerSecond = (double) Math.round(kilobytesPerSecond * 1000) / 1000;
        megabytesPerMinute = (double) Math.round(megabytesPerMinute * 1000) / 1000;

        return new ProxyInfoOutput(
                true,
                "fetched data",
                totalTransfer,
                kilobytesPerSecond,
                megabytesPerMinute,
                server.isRunning(),
                server.getConnectionsMade(),
                config.getDailyLimit() - server.getConnectionsMade()
        );
    }

}
