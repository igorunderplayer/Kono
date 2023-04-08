package me.igorunderplayer.kono.utils

import me.igorunderplayer.kono.entities.UserDB
import org.litote.kmongo.coroutine.CoroutineCollection

suspend fun getOrCreateDBUser(usersCollection: CoroutineCollection<UserDB>, id: String): UserDB? {
    var dbUser = usersCollection.findOneById(id)

    if (dbUser == null) {
        usersCollection.insertOne(
            UserDB(
                id,
                0
            )
        )

        dbUser = usersCollection.findOneById(id)
    }

    return dbUser
}