package me.igorunderplayer.kono

import java.io.File
import java.io.FileInputStream
import java.util.*

class Config {
    val token: String get() = properties.getProperty("TOKEN")

    private val properties = Properties()

    fun load(path: String = "./config.properties") {
        try {
            val file = File(path)
            FileInputStream(file).use { properties.load(it) }
        } catch (_: Exception) {
            properties.setProperty("TOKEN", System.getenv("TOKEN"))
        }
    }
}