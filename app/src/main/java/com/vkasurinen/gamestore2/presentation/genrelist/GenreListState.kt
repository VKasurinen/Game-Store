package com.vkasurinen.gamestore2.presentation.genrelist

import com.vkasurinen.gamestore2.domain.model.Genre

data class GenreListState(
    val isLoading: Boolean = false,
    val genreListPage: Int = 1,
    val genreList: List<Genre> = emptyList()
)