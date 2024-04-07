package xyz.mythrium.backend.component.bot;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import xyz.mythrium.backend.service.bot.WorkerScriptService;
import xyz.mythrium.backend.service.bot.WorkerService;

import java.util.EventListener;
import java.util.List;

@Component
public class DiscordBot {

    private final DiscordConfig config;
    private final AccountService accountService;
    private final NoteService noteService;
    private final WorkerService workerService;
    private final WorkerScriptService workerScriptService;


    private GatewayDiscordClient client = null;

    @Autowired
    public DiscordBot(DiscordConfig config, AccountService accountService, NoteService noteService, WorkerService workerService, WorkerScriptService workerScriptService) {
        this.config = config;
        this.accountService = accountService;
        this.noteService = noteService;
        this.workerService = workerService;
        this.workerScriptService = workerScriptService;

        GatewayDiscordClient client = DiscordClientBuilder.create(config.getTOKEN())
                .build()
                .login()
                .block();
    }


    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return client;
    }
}
