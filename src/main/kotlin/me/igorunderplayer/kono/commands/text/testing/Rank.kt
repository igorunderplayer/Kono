package me.igorunderplayer.kono.commands.text.testing

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.embed
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.utils.getOrCreateDBGuild

class Rank : BaseCommand(
    "rank",
    "mostra o rank de usuarios"
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val discordGuild = event.getGuildOrNull() ?: return
        val guild = getOrCreateDBGuild(Kono.db.guildsCollection, discordGuild.id.toString())!!

        val sorted = guild.levelingInfo.toList().sortedBy {
            it.second.actualXp + (it.second.level + 1) / 2 * 1500
        }.reversed()

        val count = if (sorted.size > 10) 10 else sorted.size - 1
        val strings = sorted.slice(IntRange(0, count)).mapIndexed { index, level ->
            "`${index + 1}` | <@${level.second.userId}> = Nível ${level.second.level} - ${level.second.actualXp}/??XP"
        }

        event.message.reply {
            embed {
                description = strings.joinToString("\n")
            }
        }
    }
}