package com.krushiler.features.user

import com.krushiler.features.user.data.UserRepository
import com.krushiler.features.user.dto.GetUsersResponse
import com.krushiler.features.user.dto.RegisterRequest
import com.krushiler.features.user.dto.RegisterResponse
import com.krushiler.util.respondError
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userEndpoint() {

    val userRepository by inject<UserRepository>()

    post("/register") {
        val request = call.receive<RegisterRequest>()
        try {
            val user = userRepository.register(
                nickname = request.nickname,
                password = request.password
            )
            call.respond(RegisterResponse.fromUser(user))
        } catch (e: IllegalArgumentException) {
            call.respondError(e.localizedMessage)
        }
    }

    get {
        val users = userRepository.getAllUsers()
        call.respond(
            GetUsersResponse(
                users = users.map {
                    it.nickname
                }
            )
        )
    }
}