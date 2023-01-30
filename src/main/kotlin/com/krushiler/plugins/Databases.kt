package com.krushiler.plugins

import org.jetbrains.exposed.sql.Database

fun configureDatabase() =
    Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )