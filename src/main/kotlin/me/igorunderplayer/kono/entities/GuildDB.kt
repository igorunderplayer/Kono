package me.igorunderplayer.kono.entities

import me.igorunderplayer.kono.data.dto.LevelInfo
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty

data class GuildDB @BsonCreator constructor(
    @BsonId
    val id: String,

    @BsonProperty
    val levelingInfo: HashMap<String, LevelInfo> = hashMapOf()
)