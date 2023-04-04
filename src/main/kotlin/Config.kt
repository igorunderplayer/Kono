package me.igorunderplayer.kono

import java.io.File
import java.io.FileInputStream
import java.util.*

// Saw this on https://github.com/davidffa/D4rkBotKt/blob/main/src/main/kotlin/me/davidffa/d4rkbotkt/Credentials.kt
class Config {
    val token: String get() = properties.getProperty("TOKEN")

    private val properties = Properties()

    fun load(path: String = "./config.properties"): Config {
        try {
            val file = File(path)
            FileInputStream(file).use { properties.load(it) }
        } catch (_: Exception) {
            properties.setProperty("TOKEN", System.getenv("TOKEN"))
        }

        return this
    }
}