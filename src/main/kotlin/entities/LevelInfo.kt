package me.igorunderplayer.kono.entities

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty

data class LevelInfo @BsonCreator constructor (
    @BsonId
    val userId: String,

    @BsonProperty
    var actualXp: Long = 0,

    @BsonProperty
    var level: Int = 0,
)