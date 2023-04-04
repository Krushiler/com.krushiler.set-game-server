package com.krushiler.util

import com.krushiler.dto.BaseResponseDto
import com.krushiler.dto.ExceptionDto
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondSuccess() =
    respond(
        BaseResponseDto(
            success = true
        )
    )

suspend fun ApplicationCall.respondError(message: String = "") =
    respond(
        BaseResponseDto(
            success = false, exception = ExceptionDto(message)
        )
    )