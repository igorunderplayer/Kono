package me.igorunderplayer.kono.data.repository

import kotlinx.coroutines.flow.Flow
import me.igorunderplayer.kono.data.dto.GuildDTO
import org.litote.kmongo.coroutine.CoroutineCollection

class GuildRepositoryImpl(
    private val guildCollection: CoroutineCollection<GuildDTO>
): GuildRepository {
    override suspend fun getGuildById(id: ULong): GuildDTO? {
        return guildCollection.findOneById(id)
    }

    override suspend fun getGuilds(): Flow<GuildDTO> {
        return guildCollection.find().toFlow()
    }

    override suspend fun createGuild(data: GuildDTO): GuildDTO? {
        val result = guildCollection.insertOne(data)
        return result.insertedId?.let { getGuildById(it.asInt64().value.toULong()) }
    }

    override suspend fun updateById(id: ULong, data: GuildDTO) {
        guildCollection.updateOneById(id, data)
    }
}