package me.igorunderplayer.kono.events

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import me.igorunderplayer.kono.Kono

suspend fun onChatInputCommandCreate(event: ChatInputCommandInteractionCreateEvent) {
    Kono.commands.handleChatInputCommand(event)
}