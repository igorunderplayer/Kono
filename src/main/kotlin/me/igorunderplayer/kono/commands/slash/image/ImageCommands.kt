package me.igorunderplayer.kono.commands.slash.image

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.application.GlobalChatInputCommand
import dev.kord.core.entity.interaction.SubCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.*
import me.igorunderplayer.kono.commands.KonoSlashCommand

class ImageCommands: KonoSlashCommand {
    override val name = "image"
    override val description = "comandos relacionados a manipulação de imagem"


    private val border = Border()
    private val pixelate = Pixelate()

    override suspend fun setup(kord: Kord): GlobalChatInputCommand {
        return kord.createGlobalChatInputCommand(this.name, this.description) {
            subCommand(border.name, border.description) {
                user("user", "usuario") {
                    required = true
                }
                string("color", "cor em hexadecimal (ex: #FFFFFF)")
            }

            subCommand(pixelate.name, pixelate.description) {
                attachment("image", "imagem para ser usada") {
                    required = true
                }

                number("pixel_size", "pixel size")
            }
        }
    }

    override suspend fun run(event: ChatInputCommandInteractionCreateEvent) {
        val cmd = event.interaction.command as SubCommand

        when(cmd.name) {
            border.name -> {
                border.run(event)
            }
            pixelate.name -> {
                pixelate.run(event)
            }
            else -> {
                event.interaction.respondEphemeral {
                    content = "algo de errado não está certo"
                }
            }
        }
    }
}