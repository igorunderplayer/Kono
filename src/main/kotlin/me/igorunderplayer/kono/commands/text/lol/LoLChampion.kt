package me.igorunderplayer.kono.commands.text.lol

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.create.embed
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory

class LoLChampion : BaseCommand(
    "lolchampion",
    "mostra umas infos de um champion",
    category = CommandCategory.LoL
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val query = args.joinToString(" ")
        val latestVersion = Kono.riot.dDragonAPI.versions[0]

        val champion = Kono.riot.dDragonAPI.getChampions(latestVersion, "pt_BR").values.find {
            it.name.lowercase() == query.lowercase()
        } ?: return

        val defaultSplash = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${champion.key}_0.jpg"

        event.message.reply {
            embed {
                description = champion.lore
                image = defaultSplash
                thumbnail {
                    url = "http://ddragon.leagueoflegends.com/cdn/$latestVersion/img/champion/${champion.image.full}"
                }
            }
        }
    }
}