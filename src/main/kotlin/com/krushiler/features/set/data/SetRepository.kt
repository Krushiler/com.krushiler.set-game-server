package com.krushiler.features.set.data

import com.krushiler.features.set.model.Card
import com.krushiler.features.set.model.SetGame

class SetRepository {
    private val userGames = mutableMapOf<Int, SetGame>()
    private val games = mutableListOf<SetGame>()

    private val userConnections = mutableMapOf<Int, SetGameConnection>()

    fun startGame(userId: Int): Int {
        val game = SetGame()
        games.add(game)
        userGames[userId] = game
        return game.gameId
    }

    fun enterGame(gameId: Int, userId: Int) {
        try {
            val neededGame = games.first {
                it.gameId == gameId
            }
            userGames[userId] = neededGame
        } catch (e: NoSuchElementException) {
            throw IllegalArgumentException("No such gameId")
        }
    }

    fun getCards(userId: Int): List<Card> =
        userGames[userId]?.getField() ?: throw IllegalArgumentException("No current game for you")


    fun getGames(): List<SetGame> = games

    suspend fun pickCards(userId: Int, pickedCardsIds: List<Int>): Boolean {
        val game = userGames[userId]
        userConnections
            .filter {
                userGames[it.key]?.gameId == game?.gameId
            }
            .forEach {
                it.value.sendGameStateUpdated()
            }
        return userGames[userId]?.pickCards(pickedCardsIds) ?: false
    }

    fun setUserConnection(userConnection: SetGameConnection, userId: Int) {
        userConnections[userId] = userConnection
    }

    fun removeUserConnection(userId: Int) {
        userConnections.remove(userId)
    }
}