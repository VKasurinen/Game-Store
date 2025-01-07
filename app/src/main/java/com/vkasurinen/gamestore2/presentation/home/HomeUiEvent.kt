package com.vkasurinen.gamestore2.presentation.home

sealed interface HomeUiEvent {
    data object Navigate : HomeUiEvent
    data class GenreSelected(val genre: String) : HomeUiEvent
}