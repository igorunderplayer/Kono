package me.igorunderplayer.kono.utils

import me.igorunderplayer.kono.entities.GuildDB
import org.litote.kmongo.coroutine.CoroutineCollection

suspend fun getOrCreateDBGuild(guildsCollection: CoroutineCollection<GuildDB>, id: String): GuildDB? {
    var dbGuild = guildsCollection.findOneById(id)

    if (dbGuild == null) {
        guildsCollection.insertOne(
            GuildDB(
                id,
                hashMapOf()
            )
        )

        dbGuild = guildsCollection.findOneById(id)
    }

    return dbGuild
}