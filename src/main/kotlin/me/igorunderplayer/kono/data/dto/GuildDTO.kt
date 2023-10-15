package me.igorunderplayer.kono.data.dto

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId

data class GuildDTO @BsonCreator constructor(
    @BsonId
    val id: ULong = 0u,
)