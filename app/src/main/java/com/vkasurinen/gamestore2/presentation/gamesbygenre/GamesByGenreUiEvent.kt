package com.vkasurinen.gamestore2.presentation.gamesbygenre

sealed interface GamesByGenreUiEvent {
    data class Paginate(val genre: String) : GamesByGenreUiEvent
    data class Navigate(val genre: String) : GamesByGenreUiEvent
}