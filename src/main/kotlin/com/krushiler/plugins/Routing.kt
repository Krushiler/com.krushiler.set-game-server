package com.krushiler.plugins

import com.krushiler.features.set.setEndpoint
import com.krushiler.features.user.userEndpoint
import io.ktor.server.application.*
import io.ktor.server.routing.*

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
