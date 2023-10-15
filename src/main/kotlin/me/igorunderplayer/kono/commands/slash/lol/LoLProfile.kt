package me.igorunderplayer.kono.commands.slash.lol

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.application.GlobalChatInputCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.string
import dev.kord.rest.builder.message.modify.embed
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.KonoSlashCommand
import me.igorunderplayer.kono.utils.formatNumber
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType

class LoLProfile() : KonoSlashCommand {
    override val name: String = "lolprofile"
    override val description: String = "lolprofile"
    override suspend fun setup(kord: Kord): GlobalChatInputCommand {
        return kord.createGlobalChatInputCommand(
            this.name,
            this.description
        ) {
            string("summoner", "summoner's name") {
                required = true
            }

            string("region", "summoner's region") {
                for (shard in LeagueShard.values()) {
                    choice(shard.prettyName(), shard.value)
                }
            }
        }
    }

    override suspend fun run(event: ChatInputCommandInteractionCreateEvent) {
        val response = event.interaction.deferPublicResponse()
        val queryName = event.interaction.command.strings["summoner"]
        val queryRegion = event.interaction.command.strings["region"]

        val blank = "<:transparent:1142620050952556616>"
        val champions = Kono.riot.dDragonAPI.champions
        val summoner = Kono.riot.loLAPI.summonerAPI.getSummonerByName(LeagueShard.fromString(queryRegion).get(), queryName) ?: return
        val summonerIcon = Kono.riot.dDragonAPI.profileIcons[summoner.profileIconId.toLong()]!!

        response.respond {
            embed {
                author {
                    name = "${summoner.name} - ${summoner.platform}"
                    icon = "http://ddragon.leagueoflegends.com/cdn/${Kono.riot.dDragonAPI.versions[0]}/img/profileicon/${summonerIcon.image.full}"
                }

                field {
                    name = "Ranqueado"
                    inline = true
                    value = summoner.leagueEntry
                        .filter { it.queueType != GameQueueType.CHERRY }
                        .joinToString("\n") { leagueEntry ->
                            val type = leagueEntry.queueType.prettyName().replace("5v5", "")
                            val rank = leagueEntry.rank
                            val rankTier = leagueEntry.tier
                            val pdl = leagueEntry.leaguePoints

                            "${type}: $rankTier $rank ($pdl PDL) $blank"
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