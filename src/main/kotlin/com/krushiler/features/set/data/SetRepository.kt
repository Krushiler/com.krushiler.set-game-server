package com.krushiler.features.set.data

import com.krushiler.features.set.model.Card
import com.krushiler.features.set.model.SetGame

class SetRepository {
    private var gameId = 0
    private val games = mutableMapOf<Int, SetGame>()
    private val userGames = mutableMapOf<Int, SetGame>()

    fun startGame(userId: Int): Int {
        gameId++
        games[gameId] = SetGame()
        userGames[userId] = games[gameId]!!

        return gameId
    }

    fun enterGame(gameId: Int, userId: Int) {
        userGames[userId] = games[gameId] ?: throw IllegalArgumentException("No such gameId")
    }

    fun getCards(userId: Int): List<Card> =
        userGames[userId]?.getField() ?: throw IllegalArgumentException("No current game for you")

    fun pickCards(userId: Int, pickedCardsIds: List<Int>): Boolean {
        return userGames[userId]?.pickCards(pickedCardsIds) ?: false
    }
}