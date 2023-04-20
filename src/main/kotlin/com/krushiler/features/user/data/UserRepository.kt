package com.krushiler.features.user.data

import com.krushiler.features.user.model.User
import com.krushiler.util.generateAccessToken

class UserRepository(private val userDataSource: UserDataSource) {
    suspend fun register(nickname: String, password: String): User {
        val existingUser = userDataSource.read(nickname)

        if (existingUser != null) {
            if (password == existingUser.password) {
                return existingUser
            }
            throw IllegalArgumentException("Nickname or password is incorrect")
        }

        var accessToken: String
        do {
            accessToken = generateAccessToken()
        } while (!userDataSource.tokenIsFree(accessToken))

        return userDataSource.create(
            nickname = nickname,
            accessToken = accessToken,
            password = password
        )
    }

    suspend fun getUserByToken(token: String): User =
        userDataSource.getUserByToken(token) ?: throw IllegalArgumentException("User does not exist")

    suspend fun getUserById(id: Int): User =
        userDataSource.getUserById(id) ?: throw IllegalArgumentException("User does not exist")


    suspend fun getAllUsers(): List<User> = userDataSource.getAll()
}