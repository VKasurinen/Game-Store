package com.vkasurinen.gamestore2.presentation.genrelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vkasurinen.gamestore2.presentation.components.GameItem
import com.vkasurinen.gamestore2.presentation.components.GenreItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenreScreenRoot(
    navController: NavHostController,
    viewModel: GenreListViewModel = koinViewModel(),
) {
    val state = viewModel.genreListState.collectAsState().value
    GenreScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GenreListUiEvent.Navigate -> {
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
    state: GenreListState,
    onAction: (GenreListUiEvent) -> Unit,
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
                GenreItem(
                    genre = state.genreList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}