package com.krushiler.features.set.model

sealed class SetGameState (val name: String) {
    object Ongoing : SetGameState("ongoing")
    object Ended : SetGameState("ended")
}