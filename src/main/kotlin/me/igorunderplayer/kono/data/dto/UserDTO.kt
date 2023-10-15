package me.igorunderplayer.kono.data.dto

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
data class UserDTO @BsonCreator constructor(
    @BsonId
    val id: String = "",

    @BsonProperty
    val koins: Long = 0
)