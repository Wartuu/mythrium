package xyz.mythrium.backend.component.bot.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.util.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.mythrium.backend.component.proxy.ProxyServer;

import java.awt.*;
import java.time.Instant;

@Component
public class ProxyCommand implements Command {

    private final ProxyServer server;

    @Autowired
    public ProxyCommand(ProxyServer proxyServer) {
        this.server = proxyServer;
    }

    @Override
    public String getName() {
        return "proxy";
    }

    @Override
    public InteractionApplicationCommandCallbackReplyMono execute(ApplicationCommandInteractionEvent event) {
        double totalTransfer = (double) server.getTotalBytesTransferred() / 1_073_741_824;
        double megabytesPerSecond = server.getBytesPerSecond() / 1_048_576;

        totalTransfer = (double) Math.round(totalTransfer * 1000) / 1000;
        megabytesPerSecond = (double) Math.round(megabytesPerSecond * 1000) / 1000;

        double transferLeft = server.getConfig().getUsageLimit() - totalTransfer;

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .title("Proxy status")
                .author("mythrium.xyz", "https://mythrium.xyz", null)
                .color(Color.of(0x6a5acd))
                .addField("Status", server.isRunning() ? "Up" : "Down", true)
                .addField("", "", false)
                .addField("Current Usage (MB/s)", String.format("%.3f MB/s", megabytesPerSecond), true)
                .addField("Transfer Left", String.format("%.3f GB", transferLeft), true)
                .addField("", "", false)
                .addField("Total Transfer", String.format("%.3f GB", totalTransfer), true)
                .addField("Connections Made", String.valueOf(server.getConnectionsMade()), true)
                .footer("DiscordMythrium 0.1", null)
                .timestamp(Instant.now())
                .build();


        return event.reply().withEmbeds(embed);
    }

    @Override
    public ApplicationCommandRequest initializeCommand() {
        ApplicationCommandRequest command = ApplicationCommandRequest.builder()
                .name(getName())
                .description("display informations about proxy").build();

        return command;
    }
}
