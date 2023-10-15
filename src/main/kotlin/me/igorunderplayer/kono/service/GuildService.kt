package me.igorunderplayer.kono.service

import dev.kord.common.entity.Snowflake
import kotlinx.coroutines.flow.Flow
import me.igorunderplayer.kono.data.dto.GuildDTO
import me.igorunderplayer.kono.data.repository.GuildRepository

class GuildService(
    private val repository: GuildRepository
) {
    suspend fun getGuild(id: Snowflake): GuildDTO? {
        return repository.getGuildById(id.value)
    }

    suspend fun getGuilds(): Flow<GuildDTO> {
        return repository.getGuilds()
    }

    suspend fun updateById(id: Snowflake, data: GuildDTO) {
        return repository.updateById(id.value, data)
    }
}