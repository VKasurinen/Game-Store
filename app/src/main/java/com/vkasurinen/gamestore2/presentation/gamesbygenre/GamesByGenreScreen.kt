package com.vkasurinen.gamestore2.presentation.gamesbygenre

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vkasurinen.gamestore2.presentation.components.GameItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun GamesByGenreScreenRoot(
    navController: NavHostController,
    genre: String,
    viewModel: GamesByGenreViewModel = koinViewModel()
) {
    val state = viewModel.gamesByGenreState.collectAsState().value

    LaunchedEffect(genre) {
        viewModel.onEvent(GamesByGenreUiEvent.Navigate(genre))
    }

    GamesByGenreScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GamesByGenreUiEvent.Navigate -> {
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
private fun GamesByGenreScreen(
    state: GamesByGenreState,
    onAction: (GamesByGenreUiEvent) -> Unit,
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
            items(state.gamesList.size) { index ->
                GameItem(
                    game = state.gamesList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}