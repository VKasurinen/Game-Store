package com.vkasurinen.gamestore2.presentation.details

import com.vkasurinen.gamestore2.domain.model.Game

data class DetailsState(
    val isLoading: Boolean = false,
    val game: Game? = null,
)