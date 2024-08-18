package xyz.mythrium.backend.component.bot.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.discordjson.json.ApplicationCommandRequest;

public interface Command {
    String getName();
    InteractionApplicationCommandCallbackReplyMono execute(ApplicationCommandInteractionEvent event);
    ApplicationCommandRequest initializeCommand();
}
