package me.igorunderplayer.kono.commands.testing

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.rest.Image
import io.ktor.client.request.forms.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory
import me.igorunderplayer.kono.utils.getMentionedUser
import java.awt.Color
import java.awt.Font
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.imageio.ImageIO

class Profile : BaseCommand(
    "profile",
    "exibe o perfil de alguem",
    category = CommandCategory.Misc,
    aliases = listOf("perfil")
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val width = 512
        val height = 512

        val user = getMentionedUser(event.message, args)

        if (user == null) {
            event.message.reply {
                content = "Usuario não encontrado"
            }

            return
        }

        val profileImageBuffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g2 = profileImageBuffer.createGraphics()
        g2.color = Color.DARK_GRAY
        g2.fillRect(0, 0, width, height)


        // Draw info
        val nameFont = Font("Arial", Font.ITALIC, 32)
        val subInfoFont = Font("Arial", Font.ITALIC, 24)
        g2.font = nameFont
        g2.paint = Color.WHITE
        g2.drawString(user.username, 152, 76)
        g2.font = subInfoFont
        g2.paint = Color.LIGHT_GRAY
        g2.drawString('#' + user.discriminator, 152, 110)

        // Draw avatar
        val avatar = URL((user.avatar ?: user.defaultAvatar).cdnUrl.toUrl {
            format = Image.Format.PNG
            size = Image.Size.Size128
        })

        val avatarImage = withContext(Dispatchers.IO) {
            ImageIO.read(avatar)
        }

        g2.clip = Ellipse2D.Float(12f, 12f, 128f, 128f)
        g2.drawImage(avatarImage, 12, 12, 128, 128, null)


        ByteArrayOutputStream().use {
            ImageIO.write(profileImageBuffer, "png", it)
            val imageByteArray = it.toByteArray()
            event.message.reply {
                addFile("profile.png", ChannelProvider { ByteReadChannel(imageByteArray) })
            }

            g2.dispose()
            profileImageBuffer.flush()
            avatarImage.flush()
            it.flush()
        }
    }
}