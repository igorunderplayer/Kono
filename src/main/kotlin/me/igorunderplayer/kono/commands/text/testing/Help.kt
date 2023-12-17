package me.igorunderplayer.kono.commands.text.testing

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.embed
import me.igorunderplayer.kono.Kono
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory

class Help: BaseCommand(
    "help",
    "ajuda um necessitado",
    category = CommandCategory.Util,
    aliases = listOf("ajuda", "mimajude")
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {

        val cmd = Kono.commands.searchCommand(args.firstOrNull() ?: "") ?: return displayAllCommands(event)

        val aliasesString = if (cmd.aliases.isEmpty()) "⚠️ Nenhum alias registrado" else cmd.aliases.joinToString(", ")

        event.message.reply {
            embed {
                title = cmd.name.uppercase()
                description = cmd.description

                field {
                    name = "Categoria"
                    value = cmd.category.name
                }
                field {
                    name = "Aliases (apelidos)"
                    value = aliasesString
                }
            }
        }
    }

    private suspend fun displayAllCommands(event: MessageCreateEvent) {
        val utilCommands = Kono.commands.commandList.filter {
            it.category == CommandCategory.Util
        }.map {
            "▸ ${it.name} - `${it.description}`"
        }

        val miscCommands = Kono.commands.commandList.filter {
            it.category == CommandCategory.Misc
        }.map {
            "▸ ${it.name} - `${it.description}`"
        }

        val managementCommands = Kono.commands.commandList.filter {
            it.category == CommandCategory.Management
        }.map {
            "▸ ${it.name} - `${it.description}`"
        }

        val lolCommands = Kono.commands.commandList.filter {
            it.category == CommandCategory.LoL
        }.map {
            "▸ ${it.name} - `${it.description}`"
        }

        val otherCommands = Kono.commands.commandList.filter {
            it.category == CommandCategory.Other
        }.map {
            "▸ ${it.name} - `${it.description}`"
        }

        event.message.channel.createEmbed {
            field {
                name = "\uD83E\uDDD0 Utilidade"
                value = utilCommands.joinToString("\n")
            }
            field {
                name = "\uD83E\uDD73 Miscelânea"
                value = miscCommands.joinToString("\n")
            }
            field {
                name = "\uD83D\uDEE0 Gerenciamento"
                value = managementCommands.joinToString("\n")
            }
            field {
                name = "\uD83C\uDF08 League of Legends"
                value = lolCommands.joinToString("\n")
            }
            field {
                name = "\uD83E\uDD14 Outros"
                value = otherCommands.joinToString("\n")
            }
        }
    }
}
