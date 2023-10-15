package me.igorunderplayer.kono.commands.text.lol

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.embed
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard

class LoLMatches : BaseCommand(
    "lolmatches",
    "mostra partidas de tal user",
    category = CommandCategory.LoL
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {

        // TODO: Embed pagination

        val query = args.joinToString(" ")

        val summoner = Kono.riot.loLAPI.summonerAPI.getSummonerByName(LeagueShard.BR1, query)
        val summonerIcon = Kono.riot.dDragonAPI.profileIcons[summoner.profileIconId.toLong()]!!
        val matches = Kono.riot.loLAPI.matchAPI.getMatchList(RegionShard.AMERICAS, summoner.puuid, null, null, 0, 5, null, null)

        val embedFields = matches.map { matchId ->
            val field = EmbedBuilder.Field()
            val match = Kono.riot.loLAPI.matchAPI.getMatch(RegionShard.AMERICAS, matchId)
            val self = match.participants.find { it.puuid == summoner.puuid }!!

            val emoji = Kono.emojis.firstOrNull { it.name == "lolchampion_${self.championName}" }
            val csScore = self.totalMinionsKilled + self.neutralMinionsKilled

            val emojiText = emoji?.mention ?: self.championName

            field.name = if (self.didWin()) "✔ Vitória" else "❌ Derrota"
            field.value = "$emojiText | ${self.kills}/${self.assists}/${self.deaths}  - $csScore CS \n" +
                    "> ${match.gameDurationAsDuration.toMinutes()}min - `${match.gameMode.prettyName()} (${match.queue.prettyName()})`"

            field
        }.toMutableList()

        event.message.reply {
            content = "é isso ae"
            embed {
                author {
                    name = "${summoner.name} - ${summoner.platform}"
                    icon = "http://ddragon.leagueoflegends.com/cdn/${Kono.riot.dDragonAPI.versions[0]}/img/profileicon/${summonerIcon.image.full}"
                }
                fields = embedFields
            }
        }
    }
}