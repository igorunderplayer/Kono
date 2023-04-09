package me.igorunderplayer.kono.commands.testing

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.create.embed
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

        val strings = sorted.slice(IntRange(0, if (sorted.size < 10) sorted.size - 1 else 10)).mapIndexed { index, level ->
            "`${index + 1}` | <@${level.second.userId}> = Nível ${level.second.level} - ${level.second.actualXp}/${Kono.cache.getXPToLevelUP(level.second)}XP"
        }

        event.message.reply {
            embed {
                description = strings.joinToString("\n")
            }
        }
    }
}