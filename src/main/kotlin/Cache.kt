package me.igorunderplayer.kono

import dev.kord.common.entity.Snowflake
import kotlinx.coroutines.runBlocking
import me.igorunderplayer.kono.entities.GuildDB
import me.igorunderplayer.kono.entities.LevelInfo
import me.igorunderplayer.kono.utils.getOrCreateDBGuild
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Cache {
    private val levelingGuilds = hashMapOf<Snowflake, HashMap<String, LevelInfo>>()


    suspend fun start() {

        setupXPCache()

        val threadPool = Executors.newSingleThreadScheduledExecutor()
        threadPool.scheduleWithFixedDelay({

            Kono.cache.levelingGuilds.forEach {
                runBlocking {
                    Kono.db.guildsCollection.updateOneById(
                        it.key.toString(),
                        GuildDB(
                            it.key.toString(),
                            it.value
                        )
                    )
                }
            }

        }, 15, 60, TimeUnit.SECONDS)
    }

    private suspend fun setupXPCache() {
        Kono.db.guildsCollection.find().toList().forEach {
            levelingGuilds[Snowflake(it.id)] = it.levelingInfo
        }
    }

    suspend fun addXPToUser(guildId: Snowflake, userId: Snowflake, xp: Long) {
        var value = LevelInfo(userId.toString(), xp)

        var guildCache = levelingGuilds[guildId]
        if (
            guildCache != null &&
            guildCache.containsKey(userId.toString())
        ) {
            value = checkLevelUp(levelingGuilds[guildId]!![userId.toString()]!!, xp)

            levelingGuilds[guildId]!![userId.toString()] = value
        } else {
            val dbGuild = getOrCreateDBGuild(Kono.db.guildsCollection, guildId.toString())

            levelingGuilds[guildId] = dbGuild!!.levelingInfo
            guildCache = levelingGuilds[guildId]

            guildCache?.set(
                userId.toString(),
                if (guildCache.containsKey(userId.toString()))
                    checkLevelUp(
                        guildCache[userId.toString()] ?: value,
                        0
                    )
                else value
            )
        }
    }

    fun getXPInfoFromUser(guildId: Snowflake, userId: Snowflake): LevelInfo {
        return levelingGuilds[guildId]?.get(userId.toString()) ?: LevelInfo(userId.toString())
    }

    private fun checkLevelUp(actualLevelInfo: LevelInfo, xp: Long): LevelInfo {
        val xpToLevelUp = getXPToLevelUP(actualLevelInfo)
        val copyInfo = actualLevelInfo.copy()

        val newXp = actualLevelInfo.actualXp + xp
        return if (newXp >= xpToLevelUp) {

            copyInfo.level += 1
            copyInfo.actualXp = newXp - xpToLevelUp

            copyInfo
        } else {
            copyInfo.actualXp += xp
            copyInfo
        }
    }

    fun getXPToLevelUP(actualLevelInfo: LevelInfo) : Long {
        return if (actualLevelInfo.level == 0) 1500 else actualLevelInfo.level / 2 * 1500L
    }
}
