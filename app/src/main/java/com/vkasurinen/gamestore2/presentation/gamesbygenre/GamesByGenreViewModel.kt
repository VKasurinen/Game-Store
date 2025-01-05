package com.vkasurinen.gamestore2.presentation.gamesbygenre


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GamesByGenreViewModel(
    private val gameListRepository: GameListRepository
) : ViewModel() {

    private var _gamesByGenreState = MutableStateFlow(GamesByGenreState())
    val gamesByGenreState = _gamesByGenreState.asStateFlow()

    fun onEvent(event: GamesByGenreUiEvent) {
        when (event) {
            is GamesByGenreUiEvent.Navigate -> {
                _gamesByGenreState.update {
                    it.copy(
                        gamesListPage = 1,
                        gamesList = emptyList()
                    )
                }
                getGamesByGenre(event.genre, false)
            }

            is GamesByGenreUiEvent.Paginate -> {
                getGamesByGenre(event.genre, true)
            }
        }
    }

    private fun getGamesByGenre(genre: String, paginate: Boolean) {
        viewModelScope.launch {
            _gamesByGenreState.update {
                it.copy(isLoading = true)
            }

            gameListRepository.getGamesByGenre(genre, _gamesByGenreState.value.gamesListPage).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _gamesByGenreState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { gamesList ->
                            _gamesByGenreState.update {
                                it.copy(
                                    gamesList = if (paginate) it.gamesList + gamesList else gamesList,
                                    gamesListPage = it.gamesListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _gamesByGenreState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }
}