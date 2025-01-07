import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkasurinen.gamestore2.domain.model.Genre
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.presentation.home.HomeState
import com.vkasurinen.gamestore2.presentation.home.HomeUiEvent
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
        getGenres()
        getGamesByGenre("racing") // Default genre
        getTopGames() // Fetch top games
    }

    fun onEvent(event: HomeUiEvent) {
        when(event) {
            is HomeUiEvent.Navigate -> TODO()
            is HomeUiEvent.GenreSelected -> getGamesByGenre(event.genre)
        }
    }

    private fun getGenres() {
        viewModelScope.launch {
            gameListRepository.getAllGenres(false).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        // Handle error
                    }
                    is Resource.Success -> {
                        result.data?.let { genres ->
                            _homeState.update {
                                it.copy(genres = genres)
                            }
                        }
                    }
                    is Resource.Loading -> {
                        // Handle loading state
                    }
                }
            }
        }
    }

    private fun getGamesByGenre(genre: String) {
        viewModelScope.launch {
            gameListRepository.getGamesByGenre(genre, 1).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _homeState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Success -> {
                        result.data?.let { games ->
                            _homeState.update {
                                it.copy(
                                    featuredGames = games,
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

    private fun getTopGames() {
        viewModelScope.launch {
            gameListRepository.getGameList(forceFetchFromRemote = false, page = 1).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _homeState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Success -> {
                        result.data?.let { games ->
                            _homeState.update {
                                it.copy(
                                    topGames = games,
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