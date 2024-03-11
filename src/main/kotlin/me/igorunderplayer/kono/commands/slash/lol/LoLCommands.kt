package me.igorunderplayer.kono.commands.slash.lol

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.application.GlobalChatInputCommand
import dev.kord.core.entity.interaction.SubCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.string
import dev.kord.rest.builder.interaction.subCommand
import me.igorunderplayer.kono.commands.KonoSlashCommand
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard

class LoLCommands(): KonoSlashCommand {
    override val name = "lol"
    override val description = "comandos relacionados a league of legends"

    private val profile = Profile()
    private val points = Points()

    override suspend fun setup(kord: Kord): GlobalChatInputCommand {
        return kord.createGlobalChatInputCommand(this.name, this.description) {

            subCommand(profile.name, profile.description) {
                string("riot-id", "summoner's riot id") {
                    required = true
                }

                string("region", "summoner's region") {
                    for (shard in LeagueShard.entries) {
                        choice(shard.prettyName(), shard.value)
                    }

                    required = true
                }
            }

            subCommand(points.name, points.description) {
                string("riot-id", "summoner's riot id") {
                    required = true
                }

                string("region", "summoner's region") {
                    for (shard in LeagueShard.entries) {
                        choice(shard.prettyName(), shard.value)
                    }

                    required = true
                }

                string("champion", "campeao") {
                    required = true
                }
            }
        }
    }

    override suspend fun run(event: ChatInputCommandInteractionCreateEvent) {
        val cmd = event.interaction.command as SubCommand

        when(cmd.name) {
            "profile" -> {
                profile.run(event)
            }
            "points" -> {
                points.run(event)
            }
            else -> {
                event.interaction.respondEphemeral {
                    content = "algo de errado não está certo"
                }
            }
        }
    }
}