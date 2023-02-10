package com.krushiler.features.set.data

import io.ktor.websocket.*
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicInteger

class SetGameConnection(private val session: DefaultWebSocketSession) {
    companion object {
        val lastId = AtomicInteger()
    }

    val id = lastId.getAndIncrement()

    suspend fun sendGameStateUpdated() {
        try {
            session.send("update")
        } catch (_: CancellationException) {}
    }
}