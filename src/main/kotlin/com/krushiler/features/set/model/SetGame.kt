package com.krushiler.features.set.model

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min

class SetGame {

    companion object {
        private val lastGameId = AtomicInteger()
    }

    val gameId = lastGameId.getAndIncrement()

    private val _cards = mutableListOf<Card>()
    val cards: List<Card>
        get() = _cards

    init {
        createCards()
    }

    fun pickCards(pickedCardsIds: List<Int>): Boolean {
        val pickedCards = _cards.filter {
            for (i in pickedCardsIds) {
                if (i == it.id) return@filter true
            }
            return@filter false
        }

        if (pickedCards.isSet()) {
            _cards.removeAll(pickedCards)
            return true
        }
        return false
    }

    fun getField() = cards.take(min(9, cards.size))

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