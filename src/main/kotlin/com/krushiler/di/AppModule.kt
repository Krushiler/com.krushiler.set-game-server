package com.krushiler.di

import com.krushiler.features.set.data.SetRepository
import com.krushiler.features.user.data.UserDataSource
import com.krushiler.features.user.data.UserRepository
import com.krushiler.plugins.configureDatabase
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import kotlin.system.measureTimeMillis

val appModule = module {
    single<Database> {
        lateinit var it: Database
        println(measureTimeMillis {
            it = configureDatabase()
        })
        return@single it
    }
    single<UserDataSource> { UserDataSource(get()) }
    single<UserRepository> { UserRepository(get()) }
    single<SetRepository> { SetRepository() }
}