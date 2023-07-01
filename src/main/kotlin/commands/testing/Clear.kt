package me.igorunderplayer.kono.commands.testing

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.json.request.BulkDeleteRequest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory

class Clear : BaseCommand(
    "clear",
    "limpa mensagens",
    category = CommandCategory.Management
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val count = args.firstOrNull()?.toIntOrNull()

        if (count == null) {
            event.message.reply {
                content = "vc precisa especificar a quantidade de mensagens"
            }
            return
        }

        val messages = event.message.channel.getMessagesBefore(event.message.id, count).map {
            it.id
        }.toList()

        event.kord.rest.channel.bulkDelete(
            event.message.channelId,
            BulkDeleteRequest(messages),
            "Mensagens apagadas por ${event.message.author?.tag} (${event.message.author?.id})"
        )

        event.message.reply {
            content = "$count mensagens apagadas por ${event.message.author?.mention}"
        }
    }
}