package me.igorunderplayer.kono.commands

import dev.kord.core.event.message.MessageCreateEvent

abstract class BaseCommand(
    val name: String,
    val description: String,
    val aliases: List<String> = listOf(),
    val category: CommandCategory = CommandCategory.Other
) {
    abstract suspend fun run(event: MessageCreateEvent, args: Array<String>)
}