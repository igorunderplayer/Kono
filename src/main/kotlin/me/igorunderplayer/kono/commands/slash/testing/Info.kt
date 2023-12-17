package me.igorunderplayer.kono.commands.slash.testing

import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.application.GlobalChatInputCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.message.embed
import kotlinx.coroutines.flow.count
import me.igorunderplayer.kono.commands.KonoSlashCommand
import java.lang.management.ManagementFactory

class Info: KonoSlashCommand {
    override val name = "info"
    override val description = "shows info"
    override suspend fun setup(kord: Kord): GlobalChatInputCommand {
        return kord.createGlobalChatInputCommand(
            this.name,
            this.description
        )
    }

    override suspend fun run(event: ChatInputCommandInteractionCreateEvent) {
        val response = event.interaction.deferPublicResponse()
        val descriptionBuilder = StringBuilder()
        val runtime = Runtime.getRuntime()
        val mb = 1024 * 1024

        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / mb
        val freeMemory = runtime.freeMemory() / mb
        val maxMemory = runtime.maxMemory() / mb
        val totalMemory = runtime.totalMemory() / mb

        descriptionBuilder.appendLine("\uD83C\uDF10 Em ${event.kord.guilds.count()} servidores")
        descriptionBuilder.appendLine()
        descriptionBuilder.appendLine("☕ Versão do java: ${System.getProperty("java.version")}")
        descriptionBuilder.appendLine("☕ Versão do kotlin: ${KotlinVersion.CURRENT}")
        descriptionBuilder.appendLine()
        descriptionBuilder.appendLine("\uD83D\uDCBB Usando ${usedMemory}MB de ram \uD83D\uDC38")
        descriptionBuilder.appendLine("\uD83D\uDCBB Memoria disponivel: ${freeMemory}MB")
        descriptionBuilder.appendLine("\uD83D\uDCBB Total alocado: ${totalMemory}MB")
        descriptionBuilder.appendLine("\uD83D\uDCBB E memoria no total: ${maxMemory}MB")
        descriptionBuilder.appendLine()
        descriptionBuilder.appendLine("#️⃣ Threads: ${ManagementFactory.getThreadMXBean().threadCount}")

        response.respond {
            embed {
                description = descriptionBuilder.toString()
                color = Color(2895667)
            }
        }
    }
}