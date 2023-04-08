package me.igorunderplayer.kono.commands.testing

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory
import me.igorunderplayer.kono.utils.getMentionedUser

class SetXP: BaseCommand(
    "addxp",
    "adiciona xp para alguem",
    category = CommandCategory.Management
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {

        val user = getMentionedUser(event.message, args) ?: return

        if (event.guildId == null) return

        Kono.cache.addXPToUser(event.guildId!!, user.id, args[1].toLong())

        event.message.reply {
            content = "XP adicionado!!"
        }
    }
}

