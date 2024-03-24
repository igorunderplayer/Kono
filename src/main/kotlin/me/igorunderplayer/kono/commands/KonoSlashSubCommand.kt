package me.igorunderplayer.kono.commands

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.SubCommandBuilder

interface KonoSlashSubCommand {
    val name: String
    val description: String

    fun options(): SubCommandBuilder.() -> Unit
    suspend fun run(event: ChatInputCommandInteractionCreateEvent)
}