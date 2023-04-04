package com.krushiler.features.user.dto

import com.krushiler.dto.BaseResponseDto
import com.krushiler.features.user.model.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val nickname: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val nickname: String,
    val accessToken: String,
) : BaseResponseDto() {
    companion object {
        fun fromUser(user: User) = RegisterResponse(
            nickname = user.nickname,
            accessToken = user.accessToken
        )
    }
}

@Serializable
data class GetUsersResponse(
    val users: List<String>
) : BaseResponseDto()