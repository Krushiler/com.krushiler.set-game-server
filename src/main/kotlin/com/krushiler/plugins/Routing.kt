package com.krushiler.plugins

import com.krushiler.features.set.setEndpoint
import com.krushiler.features.user.userEndpoint
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        route("/user") {
            userEndpoint()
        }
        route("/set") {
            setEndpoint()
        }
    }
}
