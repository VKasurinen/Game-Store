package com.vkasurinen.gamestore2.presentation.gamesbygenre

import com.vkasurinen.gamestore2.domain.model.Game

data class GamesByGenreState(
    val isLoading: Boolean = false,
    val gamesListPage: Int = 1,
    val gamesList: List<Game> = emptyList()
)