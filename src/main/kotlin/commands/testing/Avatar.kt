package me.igorunderplayer.kono.commands.testing

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.utils.getMentionedUser

class Avatar: BaseCommand(
    "avatar",
    "mostra o avatar de alguem"
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val user = getMentionedUser(event.message, args) ?: event.kord.getSelf()

        val avatar = user.avatar

        val format = if (avatar?.animated == true) "gif" else "png"

        val fullAvatarUrl = avatar?.cdnUrl?.toUrl()?.replace(avatar.format.extension, format, true) + "?size=4096"
        val avatarUrl = avatar?.cdnUrl?.toUrl()?.replace(avatar.format.extension, format, true) + "?size=2048"

        event.message.channel.createEmbed {
            description = "[Download]($fullAvatarUrl)"
            image = avatarUrl
            color = Color(2895667)
        }
    }
}

