package me.igorunderplayer.kono

import kotlinx.coroutines.*

object Launcher {
    @OptIn(DelicateCoroutinesApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            Config().load()

            val serverJob = GlobalScope.launch(Dispatchers.IO) {
                Server().start()
            }

            val botJob = GlobalScope.launch {
                Kono().start()
            }

            serverJob.join()
            botJob.join()
        }
    }
}

