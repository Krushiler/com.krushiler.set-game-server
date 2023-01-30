package com.krushiler.di

import com.krushiler.features.set.data.SetRepository
import com.krushiler.features.user.data.UserDataSource
import com.krushiler.features.user.data.UserRepository
import com.krushiler.plugins.configureDatabase
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val appModule = module {
    single<Database> {
        configureDatabase()
    }
    single<UserDataSource> { UserDataSource(get()) }
    single<UserRepository> { UserRepository(get()) }
    single<SetRepository> { SetRepository() }
}