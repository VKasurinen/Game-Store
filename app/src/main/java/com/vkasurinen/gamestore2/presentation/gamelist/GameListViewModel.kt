package com.vkasurinen.gamestore2.presentation.gamelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameListViewModel(
    private val gameListRepository: GameListRepository
) : ViewModel() {

    private var _gameListState = MutableStateFlow(GameListState())
    val gameListState = _gameListState.asStateFlow()

    init {
        getGameList(false)
    }

    fun onEvent(event: GameListUiEvent) {
        when (event) {
            GameListUiEvent.Navigate -> {
                _gameListState.update {
                    it.copy(
                        isCurrentGameScreen = !gameListState.value.isCurrentGameScreen
                    )
                }
            }
            is GameListUiEvent.Paginate -> {
                getGameList(true)
            }
            else -> {}
        }
    }

    private fun getGameList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _gameListState.update {
                it.copy(isLoading = true)
            }

            gameListRepository.getGameList(
                forceFetchFromRemote,
                gameListState.value.gameListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _gameListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { gameList ->
                            _gameListState.update {
                                it.copy(
                                    gameList = gameListState.value.gameList + gameList.shuffled(),
                                    gameListPage = gameListState.value.gameListPage + 1
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _gameListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}