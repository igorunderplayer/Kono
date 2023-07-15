package me.igorunderplayer.kono

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            Config().load()

             launch (Dispatchers.IO) {
                 Server().start()
             }

            launch (Dispatchers.IO) {
                Kono().start()
            }
        }
    }
}

