package me.igorunderplayer.kono.commands

import dev.kord.core.event.message.MessageCreateEvent

abstract class BaseCommand(
    val name: String,
    val description: String
) {
    abstract suspend fun run(event: MessageCreateEvent, args: Array<String>)
}