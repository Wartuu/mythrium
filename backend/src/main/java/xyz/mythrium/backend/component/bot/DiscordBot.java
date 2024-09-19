package xyz.mythrium.backend.component.bot;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import discord4j.rest.interaction.GlobalCommandRegistrar;
import discord4j.rest.service.ApplicationService;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.mythrium.backend.component.bot.commands.Command;
import xyz.mythrium.backend.config.IntegrationConfig;
import xyz.mythrium.backend.service.AccountService;
import xyz.mythrium.backend.service.NoteService;
import xyz.mythrium.backend.service.bot.WorkerScriptService;
import xyz.mythrium.backend.service.bot.WorkerService;

import java.util.ArrayList;
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
    private RestClient restClient;

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

        this.restClient = client.getRestClient();



        subscribeCommands();
    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return this.client;
    }

    @PreDestroy
    public void unload() {
        this.client.logout().block();
    }

    private void updateCommands() {
//        long appID = restClient.getApplicationId().block();
//        Flux.fromIterable(commands)
//                .flatMap(command -> {
//                    System.out.println("Registering command: " + command.getName());
//                    return restClient.getApplicationService()
//                            .createGlobalApplicationCommand(appID, command.initializeCommand())
//                            .doOnError(error -> {
//                                System.err.println("Failed to register command: " + command.getName());
//                                error.printStackTrace(); // Detailed error logging
//                            })
//                            .onErrorResume(error -> Mono.empty());
//                })
//                .doOnComplete(() -> System.out.println("All commands registered successfully."))
//                .subscribe();


        long appID = restClient.getApplicationId().block();
        ApplicationService applicationService = client.rest().getApplicationService();

        List<ApplicationCommandRequest> commandRequests = new ArrayList<>();

        for (Command c : commands) {
            commandRequests.add(c.initializeCommand());
            System.out.println("Adding " + c.getName() + " command");
        }

        applicationService.bulkOverwriteGlobalApplicationCommand(appID, commandRequests)
                .doOnNext(ignore -> System.out.println("Successfully registered Global Commands"))
                .doOnError(e -> System.out.println("Failed to register global commands: " + e))
                .subscribe();
    }

    private void subscribeCommands() {
        this.client.on(ApplicationCommandInteractionEvent.class, event -> {
            for (Command command : commands) {
                if (event.getCommandName().equalsIgnoreCase(command.getName())) {
                    return command.execute(event).then(Mono.empty());
                }
            }
            return event.reply("Command not found.").then(Mono.empty());
        }).onErrorResume(err -> {
            System.out.println(err.getMessage());
            return Mono.empty();
        }).subscribe();
    }


}
