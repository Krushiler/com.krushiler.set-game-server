package com.krushiler.dto

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponseDto(
    open val success: Boolean = true,
    open val exception: ExceptionDto? = null
)