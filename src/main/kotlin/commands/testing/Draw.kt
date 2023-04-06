package me.igorunderplayer.kono.commands.testing

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import io.ktor.client.request.forms.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.igorunderplayer.kono.commands.BaseCommand
import me.igorunderplayer.kono.commands.CommandCategory
import java.awt.Color
import java.awt.Font
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class Draw: BaseCommand(
    "drawtest",
    "desenio",
    category = CommandCategory.Other,
    aliases = listOf("draw")
) {
    override suspend fun run(event: MessageCreateEvent, args: Array<String>) {
        val width = 1024
        val height = 1024

        val message = args.firstOrNull() ?: "Bah"

        val imageBuffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val loliFile = withContext(Dispatchers.IO) {
            ImageIO.read(javaClass.classLoader.getResourceAsStream("images/teste.png"))
        }

        val g2 = imageBuffer.createGraphics()
        g2.color = Color.WHITE
        g2.fillRect(0, 0, width, height)
        g2.drawImage(loliFile, 0, 0, 1024, 1024, null)


        val font = Font("Arial", Font.BOLD, 80)
        g2.font = font
        g2.paint = Color.BLACK
        val fontMetrics = g2.fontMetrics
        val stringWidth = fontMetrics.stringWidth(message)
        val stringHeight = fontMetrics.ascent
        g2.paint = Color.black
        g2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4)

        withContext(Dispatchers.IO) {
            ByteArrayOutputStream().use {
                ImageIO.write(imageBuffer, "png", it)
                val imageByteArray = it.toByteArray()

                event.message.reply {
                    addFile("image.png", ChannelProvider { ByteReadChannel(imageByteArray) })
                }

                imageBuffer.flush()
                loliFile.flush()
            }
        }
    }
}