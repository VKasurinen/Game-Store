package com.vkasurinen.gamestore2.presentation.genrelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vkasurinen.gamestore2.presentation.components.GameItem
import com.vkasurinen.gamestore2.presentation.gamelist.GameListState
import com.vkasurinen.gamestore2.presentation.gamelist.GameListUiEvent
import com.vkasurinen.gamestore2.presentation.gamelist.GameListViewModel
import org.koin.androidx.compose.koinViewModel


//TEE GAMELISTREPOSITORYYN ETTÄ HAKEE GENRET JA SITTE VIEWMODELIIN MYÖS


@Composable
fun GenreScreenRoot(
    navController: NavHostController,
    viewModel: GameListViewModel = koinViewModel(),
) {
    val state = viewModel.gameListState.collectAsState().value
    GenreScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GameListUiEvent.Navigate -> {
                    // Handle navigation action
                }
                else -> Unit
            }
            viewModel.onEvent(action)
        },
        navHostController = navController
    )
}

@Composable
private fun GenreScreen(
    state: GameListState,
    onAction: (GameListUiEvent) -> Unit,
    navHostController: NavHostController
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(state.genreList.size) { index ->
                GameItem(
                    game = state.genreList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))

                /*if (index >= state.genreList.size - 1 && !state.isLoading) {
                    onAction(GameListUiEvent.Paginate)
                }

                 */
            }
        }
    }
}