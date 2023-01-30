package com.krushiler.features.set

import com.krushiler.features.set.data.SetRepository
import com.krushiler.features.set.dto.*
import com.krushiler.features.user.data.UserRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.setEndpoint() {
    val setRepository by inject<SetRepository>()
    val userRepository by inject<UserRepository>()

    post("/pick") {
        try {
            val request = call.receive<PickCardsRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            val result = setRepository.pickCards(uid, request.cards)
            call.respond(PickCardsResponse(result))
        } catch (e: IllegalArgumentException) {
            call.respond(e.localizedMessage)
        }
    }

    post("/field") {
        try {
            val request = call.receive<GetCardsRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            val result = setRepository.getCards(uid)
            call.respond(
                GetCardsResponse(
                    cards = result.map {
                        CardDto.fromCard(it)
                    }
                )
            )
        } catch (e: IllegalArgumentException) {
            call.respond(e.localizedMessage)
        }
    }

    route("/game") {
        post("/create") {
            try {
                val request = call.receive<CreateGameRequest>()
                val uid = userRepository.getUserByToken(request.accessToken).id
                setRepository.startGame(uid)
                call.respond(
                    true
                )
            } catch (e: IllegalArgumentException) {
                call.respond(e.localizedMessage)
            }
        }
        post("/enter") {
            try {
                val request = call.receive<EnterGameRequest>()
                val uid = userRepository.getUserByToken(request.accessToken).id
                setRepository.enterGame(gameId = request.gameId, userId = uid)
                call.respond(
                    true
                )
            } catch (e: IllegalArgumentException) {
                call.respond(e.localizedMessage)
            }
        }
    }
}