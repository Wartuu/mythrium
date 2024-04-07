package xyz.mythrium.backend.controller.api;


import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.presence.ClientPresence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mythrium.backend.component.bot.DiscordBot;
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
        double totalTransfer = (double) server.getTotalBytesTransferred() / 1_073_741_824;
        double megabytesPerSecond = server.getBytesPerSecond() / 1_048_576;

        totalTransfer = (double) Math.round(totalTransfer * 1000) / 1000;
        megabytesPerSecond = (double) Math.round(megabytesPerSecond * 1000) / 1000;

        return new ProxyInfoOutput(
                true,
                "fetched data",
                totalTransfer,
                megabytesPerSecond,
                server.isRunning(),
                server.getConnectionsMade(),
                config.getUsageLimit() - totalTransfer
        );
    }

}