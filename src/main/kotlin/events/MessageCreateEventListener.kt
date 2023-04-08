package me.igorunderplayer.kono.events

import dev.kord.core.event.message.MessageCreateEvent
import me.igorunderplayer.kono.Kono

suspend fun onMessageCreate(event: MessageCreateEvent) {
    if (event.message.author?.isBot == true) return

    val guildOrNull = event.getGuildOrNull()
    val memberOrNull = event.message.getAuthorAsMemberOrNull()

    if (guildOrNull != null && memberOrNull != null) {
        val xp = (1 + 0.5f * event.message.content.length).toLong()

        Kono.cache.addXPToUser(guildOrNull.id, memberOrNull.id, xp)
    }

    Kono.commands.handleCommand(event)
}