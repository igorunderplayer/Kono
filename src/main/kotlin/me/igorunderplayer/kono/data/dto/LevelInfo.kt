package me.igorunderplayer.kono.data.dto

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty


data class LevelInfo @BsonCreator constructor (
    @BsonId
    val userId: String,

    @BsonProperty
    var actualXp: Int = 0,

    @BsonProperty
    var level: Int = 0,
)