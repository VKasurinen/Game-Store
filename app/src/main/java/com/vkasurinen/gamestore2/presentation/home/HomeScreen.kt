package com.vkasurinen.gamestore2.presentation.home

import HomeViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.tooling.preview.Preview
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vkasurinen.gamestore2.R
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre
import com.vkasurinen.gamestore2.presentation.components.GameItem
import com.vkasurinen.gamestore2.presentation.components.HomeScreenCard
import com.vkasurinen.gamestore2.presentation.components.TopGamesCard
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreenRoot(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state = viewModel.homeState.collectAsState().value
    HomeScreen(
        state = state,
        onAction = viewModel::onEvent,
        navHostController = navController
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeUiEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GameCategories(
            genres = state.genres,
            onGenreSelected = { genre ->
                onAction(HomeUiEvent.GenreSelected(genre))
            }
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            FeaturedGameSection(state.featuredGames, navHostController)
        }

        TopGamesCard(games = state.featuredGames, navController = navHostController)
        
    }
}

@Composable
fun GameCategories(
    genres: List<Genre>,
    onGenreSelected: (String) -> Unit
) {
    var selectedGenre by remember { mutableStateOf(genres.firstOrNull()?.slug ?: "") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->

            val backgroundColor = if (genre.slug == selectedGenre) {
                Color(0xFF00C853)
            } else {
                Color.Transparent
            }

            val textColor = if (genre.slug == selectedGenre) {
                Color.Black
            } else {
                MaterialTheme.colorScheme.onBackground
            }

            Button(
                onClick = {
                    selectedGenre = genre.slug
                    onGenreSelected(genre.slug)
                },
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(18.dp)
                    )
            ) {
                Text(
                    text = genre.name,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FeaturedGameSection(games: List<Game>, navHostController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = "Our selection",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(games) { game ->
                HomeScreenCard(game = game, navController = navHostController)
            }
        }
    }
}



@Preview(
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(),
        onAction = {},
        navHostController = rememberNavController()
    )
}