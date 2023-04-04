package com.krushiler.features.set.dto

import com.krushiler.dto.BaseResponseDto
import com.krushiler.features.set.model.Card
import com.krushiler.features.set.model.SetGame
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: Int
) {
    companion object {
        fun fromSetGame(game: SetGame): GameDto =
            GameDto(id = game.gameId)
    }
}

@Serializable
data class CardDto(
    val id: Int,
    val color: Int,
    val shape: Int,
    val fill: Int,
    val count: Int
) {
    companion object {
        fun fromCard(card: Card) = CardDto(
            id = card.id,
            color = card.color,
            shape = card.shape,
            fill = card.fill,
            count = card.count
        )
    }
}

@Serializable
data class PickCardsRequest(
    val accessToken: String,
    val cards: List<Int>,
)

@Serializable
data class PickCardsResponse(
    val isSet: Boolean,
) : BaseResponseDto()

@Serializable
data class GetCardsRequest(
    val accessToken: String
)

@Serializable
data class GetCardsResponse(
    val cards: List<CardDto>
) : BaseResponseDto()


@Serializable
data class CreateGameRequest(
    val accessToken: String,
)

@Serializable
data class EnterGameRequest(
    val accessToken: String,
    val gameId: Int
)

@Serializable
data class GamesListRequest(
    val accessToken: String
)

@Serializable
data class GamesListResponse(
    val games: List<GameDto>
) : BaseResponseDto()