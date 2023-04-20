package com.krushiler.features.set.model

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min

class SetGame {

    companion object {
        private val lastGameId = AtomicInteger()
    }

    val gameId = lastGameId.getAndIncrement()

    private var cardsOnField: Int = 12

    private val _cards = mutableListOf<Card>()

    private val _users = mutableMapOf<Int, UserGameStats>()
    val users: Map<Int, UserGameStats> = _users

    private var _gameState: SetGameState = SetGameState.Ongoing

    val gameState get() = _gameState

    init {
        createCards()
    }

    fun addUser(userId: Int) {
        _users[userId] = UserGameStats()
    }

    fun removeUser(userId: Int) {
        _users.remove(userId)
    }

    fun pickCards(pickedCardsIds: List<Int>, userId: Int): Boolean {
        ensureGameIsOngoing()
        val pickedCards = field.filter {
            for (i in pickedCardsIds) {
                if (i == it.id) return@filter true
            }
            return@filter false
        }

        if (pickedCardsIds.size != pickedCards.size) {
            throw IllegalArgumentException("Cards doesn't exist")
        }

        if (pickedCards.isSet()) {
            _cards.removeAll(pickedCards)
            val stats = _users[userId]
            stats?.let {
                _users[userId] = it.copy(score = it.score + 1)
            }
            cardsOnField = max(12, cardsOnField - pickedCards.size)
            checkGameState()
            return true
        }
        return false
    }

    fun addCards() {
        ensureGameIsOngoing()
        if (cardsOnField == 21) {
            throw IllegalArgumentException("Cannot add more cards")
        }
        cardsOnField += 3
    }

    val field: List<Card>
        get() = _cards.take(min(cardsOnField, _cards.size))

    private fun checkGameState() {
        if (_cards.size < 21 && !_cards.hasSet()) _gameState = SetGameState.Ended
    }

    private fun createCards() {
        var cardId = 0
        for (color in 1..3) {
            for (shape in 1..3) {
                for (fill in 1..3) {
                    for (count in 1..3) {
                        cardId++
                        _cards.add(Card(cardId, color, shape, fill, count))
                    }
                }
            }
        }
        _cards.shuffle()
    }

    private fun List<Card>.isSet(): Boolean {
        if (size != 3) return false
        return (get(0).shape == get(1).shape) == (get(1).shape == get(2).shape)
                && (get(0).color == get(1).color) == (get(1).color == get(2).color)
                && (get(0).fill == get(1).fill) == (get(1).fill == get(2).fill)
                && (get(0).count == get(1).count) == (get(1).count == get(2).count)
    }

    private fun List<Card>.hasSet(): Boolean {
        for (i in indices) {
            for (j in i until size)
                for (k in j until size) {
                    val cards = listOf(get(i), get(j), get(k))
                    if (cards.isSet())
                        return true
                }
        }
        return false
    }

    private fun ensureGameIsOngoing() {
        if (gameState != SetGameState.Ongoing) {
            throw IllegalArgumentException("Game is not ongoing")
        }
    }
}

data class UserGameStats(
    val score: Int = 0
)