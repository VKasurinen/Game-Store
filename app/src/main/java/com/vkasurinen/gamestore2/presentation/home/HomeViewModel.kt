package com.vkasurinen.gamestore2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val gameListRepository: GameListRepository
) : ViewModel() {

    private var _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        getFeaturedGames()
    }

    fun onEvent(event: HomeUiEvent) {
        when(event) {
            HomeUiEvent.Navigate -> TODO()
        }
    }

    private fun getFeaturedGames() {
        viewModelScope.launch {
            _homeState.update { it.copy(isLoading = true) }

            gameListRepository.getGameList(false, 1).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _homeState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Success -> {
                        result.data?.let { gameList ->
                            _homeState.update {
                                it.copy(
                                    featuredGames = gameList,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _homeState.update { it.copy(isLoading = result.isLoading) }
                    }
                }
            }
        }
    }
}