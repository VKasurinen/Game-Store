package com.vkasurinen.gamestore2.presentation.genrelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenreListViewModel(
    private val gameListRepository: GameListRepository
) : ViewModel() {

    private var _genreListState = MutableStateFlow(GenreListState())
    val genreListState = _genreListState.asStateFlow()

    init {
        getGenreList(false)
    }

    fun onEvent(event: GenreListUiEvent) {
        when (event) {
            GenreListUiEvent.Navigate -> {
                _genreListState.update {
                    it.copy(
                        genreListPage = genreListState.value.genreListPage + 1
                    )
                }
            }

            is GenreListUiEvent.Paginate -> {
                getGenreList(true)
            }

            else -> {}
        }
    }

    private fun getGenreList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _genreListState.update {
                it.copy(isLoading = true)
            }

            gameListRepository.getAllGenres(forceFetchFromRemote).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _genreListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { genreList ->
                            _genreListState.update {
                                it.copy(
                                    genreList = genreListState.value.genreList + genreList,
                                    genreListPage = genreListState.value.genreListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _genreListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}