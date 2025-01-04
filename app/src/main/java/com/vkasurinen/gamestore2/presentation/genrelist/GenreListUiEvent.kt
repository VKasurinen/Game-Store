package com.vkasurinen.gamestore2.presentation.genrelist

sealed interface GenreListUiEvent {
    data class Paginate(val genre: String) : GenreListUiEvent
    data object Navigate : GenreListUiEvent
}