package me.igorunderplayer.kono

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import me.igorunderplayer.kono.entities.GuildDB
import me.igorunderplayer.kono.entities.UserDB
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class Database {

    lateinit var usersCollection: CoroutineCollection<UserDB>
    lateinit var guildsCollection: CoroutineCollection<GuildDB>


    private val connectionString = ConnectionString(Kono.config.mongoUri)

    private val settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .retryWrites(true)
        .build()

    private val client = KMongo.createClient(settings)
    private val db = client.getDatabase("kono-bot").coroutine

    fun start() {
        usersCollection = db.getCollection("users")
        guildsCollection = db.getCollection("guilds")
    }
}