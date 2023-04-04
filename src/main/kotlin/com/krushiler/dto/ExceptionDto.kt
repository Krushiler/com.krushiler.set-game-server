package com.krushiler.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionDto(
    val message: String
)