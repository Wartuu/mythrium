package xyz.mythrium.backend.component.bot;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.discordjson.json.ApplicationCommandRequest;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.mythrium.backend.config.IntegrationConfig;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import xyz.mythrium.backend.service.bot.WorkerScriptService;
import xyz.mythrium.backend.service.bot.WorkerService;

import java.util.EventListener;
import java.util.List;

@Component
public class DiscordBot {

    private final IntegrationConfig config;
    private final AccountService accountService;
    private final NoteService noteService;
    private final WorkerService workerService;
    private final WorkerScriptService workerScriptService;


    private GatewayDiscordClient client;

    @Autowired
    public DiscordBot(IntegrationConfig config, AccountService accountService, NoteService noteService, WorkerService workerService, WorkerScriptService workerScriptService) {
        this.config = config;
        this.accountService = accountService;
        this.noteService = noteService;
        this.workerService = workerService;
        this.workerScriptService = workerScriptService;


        client = DiscordClientBuilder.create(config.getDiscord())
                .build()
                .login()
                .block();

        this.client.updatePresence(ClientPresence.online(ClientActivity.watching("mythrium.xyz")))
                .block();

        this.client.on(ApplicationCommandInteractionEvent.class, event -> {
            return event.reply("unhandled action!");
        }).subscribe();

    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return this.client;
    }

    @PreDestroy
    public void unload() {
        this.client.logout().block();
    }
}
