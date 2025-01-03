package com.vkasurinen.gamestore2.presentation.gamelist

import com.vkasurinen.gamestore2.domain.model.Game

data class GameListState(
    val isLoading: Boolean = false,
    val isCurrentGameScreen: Boolean = true,

    val gameListPage: Int = 1,
    val genreListPage: Int = 1,

    val gameList: List<Game> = emptyList(),
    val genreList: List<Game> = emptyList(),
)