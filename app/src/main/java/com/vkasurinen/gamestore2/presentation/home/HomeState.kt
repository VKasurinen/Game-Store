package com.vkasurinen.gamestore2.presentation.home

import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre


data class HomeState(
    val isLoading: Boolean = false,
    val featuredGames: List<Game> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val topGames: List<Game> = emptyList()
)