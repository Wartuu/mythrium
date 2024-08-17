package xyz.mythrium.backend.component.bot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.mythrium.backend.component.bot.commands.Command;
import xyz.mythrium.backend.config.IntegrationConfig;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import xyz.mythrium.backend.service.bot.WorkerScriptService;
import xyz.mythrium.backend.service.bot.WorkerService;
import java.util.List;

@Component
public class DiscordBot {

    private final IntegrationConfig config;
    private final AccountService accountService;
    private final NoteService noteService;
    private final WorkerService workerService;
    private final WorkerScriptService workerScriptService;
    private final List<Command> commands;

    private GatewayDiscordClient client;

    @Autowired
    public DiscordBot(
            IntegrationConfig config,
            AccountService accountService,
            NoteService noteService,
            WorkerService workerService,
            WorkerScriptService workerScriptService,
            List<Command> commands) {
        this.config = config;
        this.accountService = accountService;
        this.noteService = noteService;
        this.workerService = workerService;
        this.workerScriptService = workerScriptService;
        this.commands = commands;

        this.client = DiscordClientBuilder.create(config.getDiscord())
                .build()
                .login()
                .block();

        this.client.updatePresence(ClientPresence.online(ClientActivity.watching("mythrium.xyz")))
                .block();

        registerCommands();
    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return this.client;
    }

    @PreDestroy
    public void unload() {
        this.client.logout().block();
    }

    private void registerCommands() {
        for (Command command : commands) {
            this.client.on(ApplicationCommandInteractionEvent.class, event -> {
                if (event.getCommandName().equalsIgnoreCase(command.getName())) {
                    return command.execute(event);
                }
                return event.reply("Command not found.");
            }).subscribe();
        }
    }
}
