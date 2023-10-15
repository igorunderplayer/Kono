package me.igorunderplayer.kono.data.repository

import kotlinx.coroutines.flow.Flow
import me.igorunderplayer.kono.data.dto.GuildDTO

interface GuildRepository {
    suspend fun getGuildById(id: ULong): GuildDTO?
    suspend fun getGuilds(): Flow<GuildDTO>
    suspend fun createGuild(data: GuildDTO): GuildDTO?
    suspend fun updateById(id: ULong, data: GuildDTO)

}