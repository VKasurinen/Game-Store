package com.vkasurinen.gamestore2.presentation.gamelist

sealed interface GameListUiEvent{
    data class Paginate(val genre: String) : GameListUiEvent
    data object Navigate : GameListUiEvent
}