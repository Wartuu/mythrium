package xyz.mythrium.backend.component.bot.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;

public interface Command {
    String getName();
    InteractionApplicationCommandCallbackReplyMono execute(ApplicationCommandInteractionEvent event);
}
