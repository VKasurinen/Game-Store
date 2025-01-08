package com.vkasurinen.gamestore2.presentation.gamelist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vkasurinen.gamestore2.presentation.components.GameItem
import com.vkasurinen.gamestore2.presentation.components.SearchBar
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreenRoot(
    navController: NavHostController,
    viewModel: GameListViewModel = koinViewModel(),
) {
    GameScreen(
        state = viewModel.gameListState.value,
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
private fun GameScreen(
    state: GameListState,
    onAction: (GameListUiEvent) -> Unit,
    navHostController: NavHostController
) {
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SearchBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                    onAction(GameListUiEvent.Search(it))
                },
                onSearch = {
                    onAction(GameListUiEvent.Search(searchQuery.value))
                }
            )
        }

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
                items(state.filteredGameList.size) { index ->
                    GameItem(
                        game = state.filteredGameList[index],
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}