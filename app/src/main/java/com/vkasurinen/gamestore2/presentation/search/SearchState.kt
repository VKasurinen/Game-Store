package com.vkasurinen.gamestore2.presentation.search

import com.vkasurinen.gamestore2.domain.model.Game
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    //val games: Flow<PagingData<Game>>? = null
)
