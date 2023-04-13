package me.igorunderplayer.kono.events

import dev.kord.core.event.gateway.ReadyEvent

suspend fun onReady(event: ReadyEvent) {
    val purple = "[0;35m"
    val reset = "\u001B[0m"

    println("$purple Ready as ${event.kord.getSelf().tag} (${event.kord.selfId}) $reset")
}