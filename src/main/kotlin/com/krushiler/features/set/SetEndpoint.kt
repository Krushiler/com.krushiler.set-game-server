package com.krushiler.features.set

import com.krushiler.features.set.data.SetGameConnection
import com.krushiler.features.set.data.SetRepository
import com.krushiler.features.set.dto.*
import com.krushiler.features.user.data.UserRepository
import com.krushiler.util.respondError
import com.krushiler.util.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject

fun Route.setEndpoint() {
    val setRepository by inject<SetRepository>()
    val userRepository by inject<UserRepository>()

    webSocket {
        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val userToken: String = frame.readText()
                val userId = userRepository.getUserByToken(userToken).id
                setRepository.setUserConnection(SetGameConnection(this), userId)
            }
        } finally {
            close()
        }
    }

    post("/pick") {
        try {
            val request = call.receive<PickCardsRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            val result = setRepository.pickCards(uid, request.cards)
            val score = setRepository.getUserStats(uid).score
            call.respond(PickCardsResponse(result, score))
        } catch (e: IllegalArgumentException) {
            call.respondError(e.localizedMessage)
        }
    }

    post("/field") {
        try {
            val request = call.receive<GetCardsRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            val result = setRepository.getCards(uid)
            val score = setRepository.getUserStats(uid).score
            val status = setRepository.getStatus(uid)
            call.respond(
                GetCardsResponse(
                    cards = result.map {
                        CardDto.fromCard(it)
                    },
                    score = score,
                    status = status.name
                )
            )
        } catch (e: IllegalArgumentException) {
            call.respondError(e.localizedMessage)
        }
    }

    post("/add") {
        try {
            val request = call.receive<AddCardsRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            setRepository.addCards(uid)
            call.respondSuccess()
        } catch (e: IllegalArgumentException) {
            call.respondError(e.localizedMessage)
        }
    }

    post("/scores") {
        try {
            val request = call.receive<ScoresRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            val scores = setRepository.getAllScoresInGame(uid)
            call.respond(ScoresResponse(
                scores.map { stats ->
                    PlayerDto(
                        name = userRepository.getUserById(stats.key).nickname,
                        score = stats.value.score
                    )
                }
            ))
            call.respondSuccess()
        } catch (e: IllegalArgumentException) {
            call.respondError(e.localizedMessage)
        }
    }

    route("/room") {
        post("/create") {
            try {
                val request = call.receive<CreateGameRequest>()
                val uid = userRepository.getUserByToken(request.accessToken).id
                val gameId = setRepository.startGame(uid)
                call.respond(CreateGameResponse(gameId))
            } catch (e: IllegalArgumentException) {
                call.respondError(e.localizedMessage)
            }
        }
        post("/enter") {
            try {
                val request = call.receive<EnterGameRequest>()
                val uid = userRepository.getUserByToken(request.accessToken).id
                setRepository.enterGame(gameId = request.gameId, userId = uid)
                call.respond(EnterGameResponse(request.gameId))
            } catch (e: IllegalArgumentException) {
                call.respondError(e.localizedMessage)
            }
        }
        post("/leave") {
            val request = call.receive<LeaveGameRequest>()
            val uid = userRepository.getUserByToken(request.accessToken).id
            setRepository.leaveGame(userId = uid)
            call.respondSuccess()
        }
        post("/list") {
            try {
                val request = call.receive<GamesListRequest>()
                userRepository.getUserByToken(request.accessToken).id
                val games = setRepository.getGames()
                call.respond(
                    GamesListResponse(
                        games = games.map {
                            GameDto.fromSetGame(it)
                        }
                    )
                )
            } catch (e: IllegalArgumentException) {
                call.respondError(e.localizedMessage)
            }
        }
    }
}