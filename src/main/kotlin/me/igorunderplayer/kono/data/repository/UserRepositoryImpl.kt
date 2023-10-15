package me.igorunderplayer.kono.data.repository

import me.igorunderplayer.kono.data.dto.UserDTO
import org.litote.kmongo.coroutine.CoroutineCollection

class UserRepositoryImpl(
    private val userCollection: CoroutineCollection<UserDTO>
) : UserRepository {
    override suspend fun getUserById(id: String): UserDTO? {
        return userCollection.findOneById(id)
    }

    override suspend fun createUser(data: UserDTO): UserDTO? {
        val result = userCollection.insertOne(data)
        return result.insertedId?.let { getUserById(it.toString()) }
    }
}