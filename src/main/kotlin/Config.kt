package me.igorunderplayer.kono

import java.io.File
import java.io.FileInputStream
import java.util.*

// Saw this on https://github.com/davidffa/D4rkBotKt/blob/main/src/main/kotlin/me/davidffa/d4rkbotkt/Credentials.kt
class Config {
    val token: String get() = properties.getProperty("TOKEN")
    val mongoUri: String get() = properties.getProperty("MONGO_URI")

    private val properties = Properties()

    fun load(path: String = "./config.properties"): Config {
        try {
            val file = File(path)
            FileInputStream(file).use { properties.load(it) }
        } catch (_: Exception) {
            properties.setProperty("TOKEN", System.getenv("TOKEN"))
            properties.setProperty("MONGO_URI", System.getenv("MONGO_URI"))
        }

        return this
    }
}