package me.igorunderplayer.kono.commands

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent

interface KonoSlashSubCommand {
    val name: String
    val description: String

    suspend fun run(event: ChatInputCommandInteractionCreateEvent)
}