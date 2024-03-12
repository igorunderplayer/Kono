package me.igorunderplayer.kono

import kotlinx.coroutines.*

object Launcher {
    @OptIn(DelicateCoroutinesApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            Config().load()

            val botJob = GlobalScope.launch {
                Kono().start()
            }

            val serverJob = GlobalScope.launch(Dispatchers.IO) {
                Server().start()
            }

            botJob.join()
            serverJob.join()
        }
    }
}

