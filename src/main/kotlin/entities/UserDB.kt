package me.igorunderplayer.kono.entities

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty

data class UserDB @BsonCreator constructor(
    @BsonId
    val id: String,

    @BsonProperty
    val koins: Long
)