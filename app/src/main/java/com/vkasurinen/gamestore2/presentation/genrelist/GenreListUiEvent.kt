package com.vkasurinen.gamestore2.presentation.genrelist

sealed interface GenreListUiEvent {
    data class Paginate(val genre: String) : GenreListUiEvent
    data class Navigate(val genre: String) : GenreListUiEvent
}