package com.krushiler.plugins

import org.jetbrains.exposed.sql.Database

fun configureDatabase() =
    Database.connect(
        url = "jdbc:h2:file:./build/db",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )