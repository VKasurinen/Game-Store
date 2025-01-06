package com.vkasurinen.gamestore2.presentation.home

import com.vkasurinen.gamestore2.domain.model.Game


data class HomeState(
    val isLoading: Boolean = false,
    val featuredGames: List<Game> = emptyList()
)