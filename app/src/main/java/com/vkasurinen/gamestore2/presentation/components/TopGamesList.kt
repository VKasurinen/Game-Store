package com.vkasurinen.gamestore2.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.presentation.home.HomeState

@Composable
fun TopGamesCard(games: List<Game>, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                //.background(Color.Red)
        ) {
            Text(
                text = "Top Games",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                //modifier = Modifier.padding(horizontal = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(games) { game ->
                    GameRow(game, navController)
                }
            }
        }
    }
}

@Composable
fun GameRow(game: Game, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Navigate or perform some action
                navController.navigate("gameDetail/${game.id}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Game Image
        AsyncImage(
            model = game.background_image,
            contentDescription = game.name,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 8.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "⭐ ${game.rating}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(18.dp)
                )
        ) {
            IconButton(onClick = {
                navController.navigate("details/${game.id}")
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go to game details",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(3.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TopGamesListPreview() {
    val sampleGames = listOf(
        Game(
            id = 1,
            slug = "game-1",
            name = "Race Master 3D",
            released = "2023-01-01",
            tba = false,
            background_image = "https://example.com/image1.jpg",
            rating = 4.5,
            ratingTop = 5,
            ratingsCount = 100,
            reviewsTextCount = 10,
            added = 1000,
            metacritic = 90,
            playtime = 10,
            suggestionsCount = 5,
            updated = "2023-01-01T00:00:00",
            reviewsCount = 10,
            saturatedColor = "#FFFFFF",
            dominantColor = "#000000"
        ),
        Game(
            id = 2,
            slug = "game-2",
            name = "Speed Racer",
            released = "2023-01-01",
            tba = false,
            background_image = "https://example.com/image2.jpg",
            rating = 4.7,
            ratingTop = 5,
            ratingsCount = 200,
            reviewsTextCount = 20,
            added = 1500,
            metacritic = 92,
            playtime = 12,
            suggestionsCount = 8,
            updated = "2023-01-01T00:00:00",
            reviewsCount = 15,
            saturatedColor = "#FFFFFF",
            dominantColor = "#000000"
        )
    )
    TopGamesCard(
        games = sampleGames,
        navController = rememberNavController()
    )
}
