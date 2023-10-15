package me.igorunderplayer.kono.commands

import dev.kord.core.Kord
import dev.kord.core.entity.application.GlobalChatInputCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent

interface KonoSlashCommand {
    val name: String
    val description: String

    suspend fun setup(kord: Kord): GlobalChatInputCommand
    suspend fun run(event: ChatInputCommandInteractionCreateEvent)
}