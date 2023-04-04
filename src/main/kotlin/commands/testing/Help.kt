package me.igorunderplayer.kono.commands.testing

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandManager
import me.igorunderplayer.kono.utils.getMentionedUser

class Help: BaseCommand(
    "help",
    "ajuda um necessitado"
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val commands = CommandManager.commandList.map {
            "${it.name} - ${it.description}"
        }

        event.message.channel.createEmbed {
            description = commands.joinToString("\n")
            color = Color(2895667)
        }
    }
}

