package me.igorunderplayer.kono.commands.slash.image

import dev.kord.core.Kord
import dev.kord.core.entity.application.GlobalChatInputCommand
import dev.kord.core.entity.interaction.SubCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.subCommand
import me.igorunderplayer.kono.commands.KonoSlashCommand
import me.igorunderplayer.kono.commands.slash.image.subcommand.Border
import me.igorunderplayer.kono.commands.slash.image.subcommand.Pixelate

class ImageCommands: KonoSlashCommand {
    override val name = "image"
    override val description = "comandos relacionados a manipulação de imagem"


    private val subCommands = listOf(
        Border(),
        Pixelate()
    )

    override suspend fun setup(kord: Kord): GlobalChatInputCommand {
        return kord.createGlobalChatInputCommand(this.name, this.description) {
            for (cmd in subCommands) {
                subCommand(cmd.name, cmd.description) {
                    cmd.options().invoke(this)
                }
            }
        }
    }

    override suspend fun run(event: ChatInputCommandInteractionCreateEvent) {
        val cmd = event.interaction.command as SubCommand

        subCommands.find {
            cmd.name == it.name
        }?.run(event)
    }
}