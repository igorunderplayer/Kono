package me.igorunderplayer.kono.data.repository

import me.igorunderplayer.kono.data.dto.UserDTO

interface UserRepository {
    suspend fun getUserById(id: String): UserDTO?
    suspend fun createUser(data: UserDTO): UserDTO?
}