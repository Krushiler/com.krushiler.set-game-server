package com.krushiler

import io.ktor.server.application.*
import com.krushiler.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureSerialization()
    configureSockets()
    configureRouting()
}
