package com.krushiler.features.user.data

import com.krushiler.features.user.model.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UserDataSource(private val database: Database) {
    object Users : Table() {
        val id = integer("id").autoIncrement()
        val nickname = varchar("nickname", length = 50)
        val accessToken = varchar("accessToken", length = 50)
        val password = varchar("password", length = 50)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(nickname: String, accessToken: String, password: String): User {
        val id = dbQuery {
            Users.insert {
                it[Users.nickname] = nickname
                it[Users.accessToken] = accessToken
                it[Users.password] = password
            }[Users.id]
        }
        return User(
            id = id,
            nickname = nickname,
            accessToken = accessToken,
            password = password
        )
    }

    suspend fun getAll(): List<User> = dbQuery {
        Users.selectAll().map { it.user() }
    }

    suspend fun read(id: Int): User? {
        return dbQuery {
            Users.select { Users.id eq id }
                .map {
                    it.user()
                }
                .singleOrNull()
        }
    }

    suspend fun read(nickname: String): User? {
        return dbQuery {
            Users.select { Users.nickname eq nickname }
                .map {
                    it.user()
                }
                .singleOrNull()
        }
    }

    suspend fun getUserByToken(token: String): User? {
        return dbQuery {
            Users.select { Users.accessToken eq token }
                .map {
                    it.user()
                }
                .singleOrNull()
        }
    }

    suspend fun tokenIsFree(token: String): Boolean {
        val user = dbQuery {
            Users.select { Users.accessToken eq token }
                .map {
                    it.user()
                }
                .singleOrNull()
        }
        return user == null
    }

    suspend fun update(id: Int, user: User) {
        dbQuery {
            Users.update({ Users.id eq id }) {
                it[nickname] = user.nickname
                it[accessToken] = user.accessToken
                it[password] = user.password
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Users.deleteWhere { Users.id.eq(id) }
        }
    }

    private fun ResultRow.user() = User(
        id = this[Users.id],
        nickname = this[Users.nickname],
        accessToken = this[Users.accessToken],
        password = this[Users.password]
    )
}