package me.igorunderplayer.kono.commands.testing

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import kotlinx.coroutines.flow.count
import me.igorunderplayer.kono.commands.BaseCommand
import java.lang.management.ManagementFactory

class Info: BaseCommand(
   "info",
   "mostra info"
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val descriptionBuilder = StringBuilder()

        val MB = 1024 * 1024

        val runtime = Runtime.getRuntime()

        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / MB
        val freeMemory = runtime.freeMemory() / MB
        val maxMemory = runtime.maxMemory() / MB
        val totalMemory = runtime.totalMemory() / MB


        descriptionBuilder.appendLine("Em ${event.kord.guilds.count()} servidores")
        descriptionBuilder.appendLine("☕ Versão do java: ${System.getProperty("java.version")}")
        descriptionBuilder.appendLine("☕ Versão do kotlin: ${KotlinVersion.CURRENT}")
        descriptionBuilder.appendLine()
        descriptionBuilder.appendLine("Usando ${usedMemory}MB de ram \uD83D\uDC38")
        descriptionBuilder.appendLine("Memoria disponivel: ${freeMemory}MB")
        descriptionBuilder.appendLine("Total alocado: ${totalMemory}MB")
        descriptionBuilder.appendLine("E memoria no total: ${maxMemory}MB")
        descriptionBuilder.appendLine()
        descriptionBuilder.appendLine("Threads: ${ManagementFactory.getThreadMXBean().threadCount}")



        event.message.channel.createEmbed {
            description = descriptionBuilder.toString()
            color = Color(2895667)
        }
    }
}