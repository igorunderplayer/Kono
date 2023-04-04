package me.igorunderplayer.kono

import kotlinx.coroutines.runBlocking


object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            Kono().start()
        }
    }
}

