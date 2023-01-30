package com.krushiler.features.user.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val nickname: String,
    val accessToken: String,
    val password: String
)
