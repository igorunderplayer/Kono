package me.igorunderplayer.kono.events

import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on

class EventManager(private val kord: Kord) {
    fun start() {
        kord.on<ReadyEvent> { onReady(this) }
        kord.on<MessageCreateEvent> { onMessageCreate(this) }
        kord.on<GuildChatInputCommandInteractionCreateEvent> { onChatInputCommandCreate(this) }
    }
}