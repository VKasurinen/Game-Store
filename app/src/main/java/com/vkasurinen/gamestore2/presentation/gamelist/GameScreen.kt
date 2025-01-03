package com.vkasurinen.gamestore2.presentation.gamelist

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vkasurinen.gamestore2.R
import com.vkasurinen.gamestore2.presentation.components.GameItem
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
            items(state.gameList.size) { index ->
                GameItem(
                    game = state.gameList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))

                /*if (index >= state.gameList.size - 1 && !state.isLoading) {
                    onAction(GameListUiEvent.Paginate)
                }

                 */
            }
        }
    }
}



