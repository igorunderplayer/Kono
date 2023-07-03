package me.igorunderplayer.kono.commands.lol

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.create.embed
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory
import me.igorunderplayer.kono.utils.formatNumber
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard

class LoLProfile : BaseCommand(
    "lolprofile",
    "Mostra perfil do lol de alguem",
    category = CommandCategory.LoL
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val query = args.joinToString(" ")

        val champions = Kono.riot.dDragonAPI.champions
        val summoner = Kono.riot.loLAPI.summonerAPI.getSummonerByName(LeagueShard.BR1, query) ?: return
        val summonerIcon = Kono.riot.dDragonAPI.profileIcons[summoner.profileIconId.toLong()]!!

        event.message.reply {
            embed {
                author {
                    name = "${summoner.name} - ${summoner.platform}"
                    icon = "http://ddragon.leagueoflegends.com/cdn/${Kono.riot.dDragonAPI.versions[0]}/img/profileicon/${summonerIcon.image.full}"
                }

                field {
                    name = "Ranqueado"
                    inline = true
                    value = summoner.leagueEntry.joinToString("\n") { leagueEntry ->
                        val type = leagueEntry.queueType.prettyName().replace("5v5", "")
                        val rank = leagueEntry.rank
                        val rankTier = leagueEntry.tier
                        val pdl = leagueEntry.leaguePoints

                        "${type}: $rankTier $rank ($pdl PDL)"
                    }
                }

                field {
                    name = "Melhores campeões"
                    inline = true
                    value = summoner.championMasteries.slice(IntRange(0, 4)).joinToString("\n") { championMastery ->
                        val champion = champions[championMastery.championId]!!
                        val emoji = Kono.emojis.firstOrNull { it.name == "lolchampion_${champion.key}" }
                        val iconText = emoji?.mention ?: champion.name
                        "$iconText ${champion.name} - ${formatNumber(championMastery.championPoints)}"
                    }
                }
            }
        }
    }
}