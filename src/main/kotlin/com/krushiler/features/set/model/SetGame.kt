package com.krushiler.features.set.model

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min

class SetGame {

    companion object {
        private val lastGameId = AtomicInteger()
    }

    val gameId = lastGameId.getAndIncrement()

    private val _cards = mutableListOf<Card>()

    private val _users = mutableMapOf<Int, UserGameStats>()
    val users: Map<Int, UserGameStats> = _users

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
            return true
        }
        return false
    }

    val field: List<Card> get() = _cards.take(min(9, _cards.size))

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
}

data class UserGameStats(
    val score: Int = 0
)