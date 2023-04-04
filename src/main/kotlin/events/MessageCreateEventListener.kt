package me.igorunderplayer.kono.events

import dev.kord.core.event.message.MessageCreateEvent
import me.igorunderplayer.kono.Kono

suspend fun onMessageCreate(event: MessageCreateEvent) {
    if (event.message.author?.isBot == true) return

    Kono.commands.handleCommand(event)
}