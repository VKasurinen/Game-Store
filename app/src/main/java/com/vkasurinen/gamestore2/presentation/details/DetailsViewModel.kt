package com.vkasurinen.gamestore2.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val gameListRepository: GameListRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val gameId = savedStateHandle.get<Int>("gameId")

    private var _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getGame(gameId ?: -1)
    }

    private fun getGame(id: Int) {
        viewModelScope.launch {

            _detailsState.update {
                it.copy(isLoading = true)
            }

            gameListRepository.getGame(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { game ->
                            _detailsState.update {
                                it.copy(game = game)
                            }
                        }
                    }
                }
            }

        }

    }

}