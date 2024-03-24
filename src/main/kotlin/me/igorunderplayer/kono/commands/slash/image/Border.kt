package me.igorunderplayer.kono.commands.slash.image

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.Image
import io.ktor.client.request.forms.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.igorunderplayer.kono.commands.KonoSlashSubCommand
import java.awt.Color
import java.awt.GradientPaint
import java.awt.RenderingHints
import java.awt.geom.RoundRectangle2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URI
import javax.imageio.ImageIO

class Border: KonoSlashSubCommand {
    override val name = "border"
    override val description = "cria uma bordinha pra sua foto"

    override suspend fun run(event: ChatInputCommandInteractionCreateEvent) {
        val user = event.interaction.command.users["user"]
        val color = event.interaction.command.strings["color"] ?: "#FFFFFF"
        val color2 = event.interaction.command.strings["color2"] ?: color

        val avatar = user?.avatar?.cdnUrl?.toUrl {
            format = Image.Format.JPEG
            size = Image.Size.Size2048
        }

        if (avatar == null) {
            event.interaction.respondEphemeral {
                content = "ue"
            }
            return
        }

        val response = event.interaction.deferPublicResponse()

        val uri = URI.create(avatar)
        val file = withContext(Dispatchers.IO) {
            ImageIO.read(uri.toURL())
        }

        val bgColor = Color.decode(color)
        val bgColor2 = Color.decode(color2)
        val rounded = generateAvatarBorder(file, bgColor, bgColor2)

        ByteArrayOutputStream().use {
            ImageIO.write(rounded, "png", it)
            val imageByteArray = it.toByteArray()
            response.respond {
                content = "borda ne"
                addFile("image.png", ChannelProvider { ByteReadChannel(imageByteArray) })
            }
        }
    }

    private fun generateAvatarBorder(avatar: BufferedImage, color: Color, color2: Color): BufferedImage {
        val width = avatar.width
        val height = avatar.height
        val pad = 32
        val output = BufferedImage(width + pad, height + pad, BufferedImage.TYPE_INT_ARGB)
        val g2 = output.createGraphics()


        g2.paint = GradientPaint(0F, 0F, color, 0F, height.toFloat(), color2)
        g2.fillRect(0, 0, width + pad, height + pad)

        val qualityHints = RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        qualityHints[RenderingHints.KEY_RENDERING] = RenderingHints.VALUE_RENDER_QUALITY

        g2.setRenderingHints(qualityHints)
        g2.clip = RoundRectangle2D.Float(
            (pad / 2).toFloat(), (pad / 2).toFloat(), width.toFloat(), height.toFloat(), (width).toFloat(),
            (height).toFloat()
        )
        g2.drawImage(avatar, pad / 2, pad / 2, null)

        g2.dispose()

        return output
    }
}