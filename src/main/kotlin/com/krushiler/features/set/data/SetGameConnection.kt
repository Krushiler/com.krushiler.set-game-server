package com.krushiler.features.set.data

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class SetGameConnection(private val session: DefaultWebSocketSession) {
    companion object {
        val lastId = AtomicInteger()
    }

    val id = lastId.getAndIncrement()

    suspend fun sendGameStateUpdated() {
        session.send("update")
    }
}