package me.igorunderplayer.kono.service

import dev.kord.common.entity.Snowflake
import me.igorunderplayer.kono.data.dto.UserDTO
import me.igorunderplayer.kono.data.repository.UserRepository

class UserService(
    private val repository: UserRepository
) {

    suspend fun getUser(id: Snowflake): UserDTO? {
        return repository.getUserById(id.toString())
    }

    suspend fun createUser(data: UserDTO): UserDTO? {
        return repository.createUser(data)
    }

    suspend fun getOrCreateUser(id: Snowflake): UserDTO? {
        var user = getUser(id)

        if (user == null) {
            user = createUser(
                UserDTO(
                    id = id.toString()
                )
            )
        }

        return user
    }
}